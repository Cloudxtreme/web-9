   var socket = new SockJS('/screen');
   var stompClient = Stomp.over(socket);
   
   stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/shell', function (protocol) {
        	$("#output").append(JSON.parse(protocol.body).response + "\r\n");
    	});
   });