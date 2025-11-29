package dev.mju.jobport.modules.auth.api.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SocialUserInfo(
        @JsonProperty("sub") long sub,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("picture") String picture
) {
}
