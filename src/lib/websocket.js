//웹소켓 연결 설정 정의
const ConnectConfig = {
    PROTOCOL: "ws:",
    // TODO: change to localhost if you wish to run it locally
    HOST: "//10.70.3.133",
    //HOST: "//localhost",
    PORT: ":9000",
    PATH: "/STWebSocket"
}

//웹소켓 연결 후 연결 객체 리턴
const ConnectSocket = (function () {
    let instance;
    function createInstance(uuid) {
        const socket = new WebSocket(ConnectConfig.PROTOCOL + ConnectConfig.HOST + ConnectConfig.PORT + ConnectConfig.PATH+"/"+uuid);
        return socket;
    }

    return {
        getInstance: function (uuid) {
            instance = createInstance(uuid);
            return instance;
        }
    };
})();

export default ConnectSocket;
