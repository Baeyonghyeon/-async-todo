//package com.kurt.asynctodo.security;
//
//import com.kurt.asynctodo.security.dto.Oauth2Member;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GithubOauth2MemberService extends DefaultOAuth2UserService {
//
//    /**
//     * 성공적인 요청 성공시 Spring Oauth2에 의해 호출된 loadUser() 메서드를 재정의 하고
//     * 새로운 CustomOAuth2User 객체를 반환한다.
//     */
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User user = super.loadUser(userRequest);
//        return new Oauth2Member(user);
//    }
//}
