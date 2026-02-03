export const authConfig = {
  clientId: 'oauth2-pkce-client',
  authorizationEndpoint: 'http://localhost:8081/realms/fitness-oauth2/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:8081/realms/fitness-oauth2/protocol/openid-connect/token',
  redirectUri: `${window.location.origin}`,
  scope: 'openid profile email offline_access',
  onRefreshTokenExpire: () => {
    console.log('Refresh token expired, user needs to login again');
  },
};
