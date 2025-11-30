(function () {
    const editorList = document.getElementById('cover-letter-editor-list');
    const template = document.getElementById('cover-letter-template');
    const emptyState = document.getElementById('cover-letter-empty-state');
    if (!editorList || !template) {
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
    const totalBadge = document.querySelector('[data-cover-letter-total]');
    const completeCounter = document.querySelector('[data-cover-letter-complete]');
    const draftCounter = document.querySelector('[data-cover-letter-draft]');
    const progressBar = document.getElementById('cover-letter-progress-bar');
    const addTrigger = document.getElementById('cover-letter-add-trigger');
    const emptyAddTrigger = document.getElementById('cover-letter-empty-add');
    const previewTrigger = document.getElementById('cover-letter-preview-trigger');
    const apiBasePath = '/api/cover-letters';
    const dateFormatter = new Intl.DateTimeFormat('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        hour12: false
    });

    const updateStatistics = () => {
        const total = editorList.querySelectorAll('.cover-letter-card').length;
        if (totalBadge) {
            totalBadge.textContent = `총 ${total}건`;
        }
        if (completeCounter) {
            completeCounter.textContent = String(total);
        }
        if (draftCounter) {
            draftCounter.textContent = total === 0 ? '0' : String(Math.max(total - 1, 0));
        }
        if (progressBar) {
            progressBar.style.width = `${total === 0 ? 10 : 90}%`;
        }
    };

    const updateEmptyState = () => {
        const hasItems = editorList.querySelectorAll('.cover-letter-card').length > 0;
        emptyState?.classList.toggle('d-none', hasItems);
    };

    const updateOrders = () => {
        editorList.querySelectorAll('.cover-letter-card').forEach((card, index) => {
            card.dataset.index = String(index);
            const orderEl = card.querySelector('.cover-letter-order');
            if (orderEl) {
                orderEl.textContent = String(index + 1);
            }
        });
    };

    const sendJson = async (url, method, payload) => {
        const headers = {
            'Content-Type': 'application/json'
        };
        if (csrfToken && csrfHeader) {
            headers[csrfHeader] = csrfToken;
        }
        const response = await fetch(url, {
            method,
            headers,
            body: payload ? JSON.stringify(payload) : undefined
        });

        if (!response.ok) {
            let message = '요청 처리 중 오류가 발생했습니다.';
            try {
                const errorBody = await response.json();
                if (errorBody?.message) {
                    message = errorBody.message;
                }
            } catch (e) {
                const text = await response.text();
                if (text) {
                    message = text;
                }
            }
            throw new Error(message);
        }

        if (response.status === 204) {
            return null;
        }
        return response.json();
    };

    const setButtonLoading = (button, loading) => {
        if (!button) {
            return;
        }
        button.disabled = loading;
        button.dataset.originalText = button.dataset.originalText || button.textContent;
        button.textContent = loading ? '처리 중...' : button.dataset.originalText;
    };

    const formatUpdatedAt = (value) => {
        if (!value) {
            return '임시 저장';
        }
        const date = new Date(value);
        if (Number.isNaN(date.getTime())) {
            return '임시 저장';
        }
        return dateFormatter.format(date);
    };

    const updateUpdatedAtText = (card, dateValue) => {
        const updatedAtEl = card.querySelector('.cover-letter-updated-at');
        if (updatedAtEl) {
            updatedAtEl.textContent = formatUpdatedAt(dateValue);
        }
    };

    const bindTextArea = (card) => {
        const textarea = card.querySelector('.cover-letter-textarea');
        const limitInput = card.querySelector('.cover-letter-limit-input');
        const countEl = card.querySelector('.cover-letter-text-count');
        const limitEl = card.querySelector('.cover-letter-text-limit');

        if (!textarea) {
            return;
        }

        const applyLimit = () => {
            const limit = parseInt(textarea.dataset.limit || limitInput?.value, 10) || 1000;
            if (textarea.value.length > limit) {
                textarea.value = textarea.value.slice(0, limit);
            }
            if (countEl) {
                countEl.textContent = String(textarea.value.length);
            }
        };

        const handleLimitChange = () => {
            const nextLimit = parseInt(limitInput?.value, 10) || 1000;
            textarea.dataset.limit = String(nextLimit);
            if (limitEl) {
                limitEl.textContent = String(nextLimit);
            }
            applyLimit();
        };

        textarea.addEventListener('input', applyLimit);
        if (limitInput) {
            limitInput.addEventListener('change', handleLimitChange);
            limitInput.addEventListener('blur', handleLimitChange);
        }
        handleLimitChange();
    };

    const bindSaveAction = (card) => {
        const saveBtn = card.querySelector('.cover-letter-save');
        if (!saveBtn) {
            return;
        }
        saveBtn.addEventListener('click', async () => {
            const titleInput = card.querySelector('.cover-letter-title-input');
            const textarea = card.querySelector('.cover-letter-textarea');
            if (!titleInput) {
                return;
            }
            const title = titleInput.value.trim();
            if (!title) {
                alert('문항 제목을 입력해주세요.');
                titleInput.focus();
                return;
            }
            const payload = {
                title,
                content: textarea?.value || ''
            };
            const coverLetterId = card.dataset.id;
            const method = coverLetterId ? 'PUT' : 'POST';
            const url = coverLetterId ? `${apiBasePath}/${coverLetterId}` : apiBasePath;
            try {
                setButtonLoading(saveBtn, true);
                const response = await sendJson(url, method, payload);
                if (response) {
                    card.dataset.id = response.id;
                    updateUpdatedAtText(card, response.updatedAt);
                }
                alert('임시 저장되었습니다.');
            } catch (error) {
                console.error(error);
                alert(error.message || '저장 중 문제가 발생했습니다.');
            } finally {
                setButtonLoading(saveBtn, false);
            }
        });
    };

    const bindDeleteAction = (card) => {
        const removeBtn = card.querySelector('.cover-letter-remove');
        if (!removeBtn) {
            return;
        }
        removeBtn.addEventListener('click', async () => {
            const coverLetterId = card.dataset.id;
            if (coverLetterId) {
                const confirmed = confirm('작성된 자기소개서를 삭제하시겠습니까?');
                if (!confirmed) {
                    return;
                }
                try {
                    removeBtn.disabled = true;
                    await sendJson(`${apiBasePath}/${coverLetterId}`, 'DELETE');
                } catch (error) {
                    console.error(error);
                    alert(error.message || '삭제 중 오류가 발생했습니다.');
                    removeBtn.disabled = false;
                    return;
                }
            }
            card.remove();
            updateOrders();
            updateEmptyState();
            updateStatistics();
        });
    };

    const bindDuplicateAction = (card) => {
        const duplicateBtn = card.querySelector('.cover-letter-duplicate');
        if (!duplicateBtn) {
            return;
        }
        duplicateBtn.addEventListener('click', () => {
            const clone = card.cloneNode(true);
            clone.dataset.id = '';
            updateUpdatedAtText(clone, null);
            editorList.insertBefore(clone, card.nextSibling);
            initializeCard(clone);
            updateOrders();
            updateEmptyState();
            updateStatistics();
            const titleInput = clone.querySelector('.cover-letter-title-input');
            titleInput?.focus();
        });
    };

    const bindPerCardPreview = (card) => {
        const previewBtn = card.querySelector('.cover-letter-preview-btn');
        if (!previewBtn) {
            return;
        }
        previewBtn.addEventListener('click', () => {
            const title = card.querySelector('.cover-letter-title-input')?.value?.trim() || '미입력 문항';
            const content = card.querySelector('.cover-letter-textarea')?.value?.trim() || '작성된 내용이 없습니다.';
            alert(`[${title}]\n\n${content}`);
        });
    };

    const initializeCard = (card) => {
        bindTextArea(card);
        bindSaveAction(card);
        bindDeleteAction(card);
        bindDuplicateAction(card);
        bindPerCardPreview(card);
    };

    const createCardElement = (data = {}) => {
        const fragment = template.content.cloneNode(true);
        const newCard = fragment.querySelector('.cover-letter-card');
        if (!newCard) {
            return null;
        }
        if (data.id) {
            newCard.dataset.id = data.id;
        }
        const titleInput = newCard.querySelector('.cover-letter-title-input');
        if (titleInput) {
            titleInput.value = data.title ?? '';
        }
        const textarea = newCard.querySelector('.cover-letter-textarea');
        if (textarea) {
            textarea.value = data.content ?? '';
        }
        updateUpdatedAtText(newCard, data.updatedAt);
        return newCard;
    };

    const nextDefaultTitle = () => {
        const total = editorList.querySelectorAll('.cover-letter-card').length;
        return `새 자기소개서 문항 ${total + 1}`;
    };

    editorList.querySelectorAll('.cover-letter-card').forEach(initializeCard);
    updateEmptyState();
    updateOrders();
    updateStatistics();

    const addNewCard = async (triggerButton) => {
        const defaultTitle = nextDefaultTitle();
        let title = prompt('새 문항 제목을 입력하세요.', defaultTitle);
        if (title === null) {
            return;
        }
        title = title.trim();
        if (!title) {
            title = defaultTitle;
        }

        try {
            setButtonLoading(triggerButton, true);
            const response = await sendJson(apiBasePath, 'POST', { title, content: '' });
            const newCard = createCardElement(response);
            if (!newCard) {
                return;
            }
            editorList.appendChild(newCard);
            initializeCard(newCard);
            updateOrders();
            updateEmptyState();
            updateStatistics();
            const titleInput = newCard.querySelector('.cover-letter-title-input');
            titleInput?.focus();
            titleInput?.select();
        } catch (error) {
            console.error(error);
            alert(error.message || '새 문항을 추가하지 못했습니다.');
        } finally {
            setButtonLoading(triggerButton, false);
        }
    };

    addTrigger?.addEventListener('click', () => addNewCard(addTrigger));
    emptyAddTrigger?.addEventListener('click', () => addNewCard(emptyAddTrigger));

    previewTrigger?.addEventListener('click', () => {
        const summaries = [];
        editorList.querySelectorAll('.cover-letter-card').forEach((card, index) => {
            const title = card.querySelector('.cover-letter-title-input')?.value?.trim() || `문항 ${index + 1}`;
            const contentLength = card.querySelector('.cover-letter-textarea')?.value?.length || 0;
            summaries.push(`${index + 1}. ${title} (${contentLength}자)`);
        });
        if (summaries.length === 0) {
            summaries.push('아직 작성된 문항이 없습니다.');
        }
        alert(summaries.join('\n'));
    });
})();
