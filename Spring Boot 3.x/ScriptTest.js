if(responseCode.code >= 200 && responseCode.code <= 299) {
    var jsonData = JSON.parse(responseBody)

    postman.setEnvironmentVariable("accessToken", jsonData.accessToken);
    postman.setEnvironmentVariable("refreshToken", jsonData.refreshToken);
}