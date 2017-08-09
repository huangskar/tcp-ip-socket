1. accept ,read,recive  
socket serversocket Datagramsocket setSoTimeout方法，设置其阻塞的最长时间。
对于socket实例调用read之前可以调用available方法来检测是否有可读的数据。
2. 连接和写数据  
Socket的connect方法可以指定超时时间  
java中没有听过write超时

3. keep-alive  
如果一段时间内没有数据交换，通信的每个终端可能都会怀疑对方是否还处于活跃状
态。TCP 协议提供了一种 keep-alive 的机制，该机制在经过一段不活动时间后，将向另一个
终端发送一个探测消息。如果另一个终端还出于活跃状态，它将回复一个确认消息。如果经
过几次尝试后依然没有收到另一终端的确认消息，则终止发送探测信息，关闭套接字，并在
下一次尝试 I/O 操作时抛出一个异常。注意，应用程序只要在探测信息失败时才能察觉到  
keep-alive 机制的工作。  
Socket: 保持活跃  
boolean getKeepAlive()  
void setKeepAlive(boolean on)  
默认情况下，keep-alive 机制是关闭的。通过调用 setKeepAlive()方法将其设置为 true 来
开启 keep-alive 机制。
4. 发送和接收缓存区的大小  
Socket, DatagramSocket: 设置和获取发送接收缓存区大小  
int getReceiveBufferSize()  
void setReceiveBufferSize(int size)  
int getSendBufferSize()  
void setSendBufferSize(int size)  
getReceiveBufferSize()，  setReceiveBufferSize()，  
getSendBufferSize()，  
和 setSendBufferSize()  
方法分别用于获取和设置接收发送缓冲区的大小（以字节为单位）。需要注意的是，这里指
定的大小只是作为一种建议给出的，实际大小可能与之存在差异。
还可以在 ServerSocket 上指定接收缓冲区大小。不过，这实际上是为 accept()方法所创
建的新 Socket 实例设置接收缓冲区大小。为什么可以只设置接收缓冲区大小而不设置发送
缓冲区的大小呢？当接收了一个新的 Socket，它就可以立刻开始接收数据，因此需要在
accept()方法完成连接之前设置好缓冲区的大小。另一方面，由于可以控制什么时候在新接
受的套接字上发送数据，因此在发送之前还有时间设置发送缓冲区的大小。
ServerSocket: 设置/获取所接受套接字的接收缓冲区大小  
int getReceiveBufferSize()  
void setReceiveBufferSize(int size)  
getReceiveBufferSize()和   setReceiveBufferSize()方法用于获取和设置由 accept()方法创建
的 Socket 实例的接收缓冲区的大小（字节）。
5. 超时  
如前面所介绍的，很多 I/O 操作如果不能立即完成就会阻塞等待：读操作将阻塞等待直
到至少有一个字节可读；接收操作将阻塞等待直到成功建立连接。不幸的是阻塞的时间没有
限制。可以为各种操作指定一个最大阻塞时间。
Socket, ServerSocket, DatagramSocket: 设置/获取 I/O 超时时间
int getSoTimeout()
void setSoTimeout(int timeout)
getSoTimeout()和 setSoTimeout()方法分别用于获取和设置读/接收数据操作以及 accept
操作的最长阻塞时间。超时设置为 0 表示该操作永不超时。如果阻塞超过了超时时长，则抛
出一个异常。
6.   地址重用  
在某些情况下，可能希望能够将多个套接字绑定到同一个套接字地址。对于 UDP 多播
的情况，在同一个主机上可能有多个应用程序加入了相同的多播组。对于 TCP，当一个连
接关闭后，通信的一端（或两端）必须在"Time-Wait"状态上等待一段时间，以对传输途中
丢失的数据包进行清理（见第 6.4.2 节）。不幸的是，通信终端可能无法等到 Time-Wait 结
束。对于这两种情况，都需要能够与正在使用的地址进行绑定的能力，这就要求实现地址重
用。  
Socket, ServerSocket, DatagramSocket: 设置/获取地址重用  
boolean getReuseAddress()  
void setReuseAddress(boolean on)  
getReuseAddress()和 setReuseAddress()方法用于获取和设置地址重用许可。设置为 true
表示启用了地址重用功能。
7. 消除缓冲延迟  
TCP 协议将数据缓存起来直到足够多时一次发送，以避免发送过小的数据包而浪费网
络资源。虽然这个功能有利于网络，但应用程序可能对所造成的缓冲延迟不能容忍。好在可
以人为禁用缓存功能。  
Socket: 设置/获取 TCP 缓冲延迟  
boolean getTcpNoDelay()  
void setTcpNoDelay(boolean on)  
getTcpNoDelay()和 setTcpNoDelay()方法用于获取和设置是否消除缓冲延迟。将值设置
为 true 表示禁用缓冲延迟功能。
8. 紧急数据  
假设你已经向一个慢速接收者发送了很多数据，突然又有了它急需的其它数据。如果将
这些数据发送到输出流，它们将追加在常规数据队列的后面，无法保证接收者能够立即接收。
为了解决这个问题，TCP 协议中包含了紧急（urgent）数据的概念，这类数据可以（理论上
来说）跳到前面去。由于它们能够绕过常规数据流，这些数据称为频道外数据。  
Socket: 紧急数据  
void sendUrgentData(int data)  
boolean getOOBInline()  
void setOOBInline(boolean on)  
要发送紧急数据需要调用 sendUrgentData() 方法，它将发送其 int 参数的最低位字节。
要接收这个字节，必须为 setOOBInline()方法传递 true 参数启用接收者对频道外数据的接收。
该字节在接收者的输入流中被接收。发送于紧急字节之前的数据将处于接收者的输入流中的
紧急字节前面。如果没有启用接收者接收频道外数据的功能，紧急字节将被无声地丢弃。
**注意 Java 中的紧急数据几乎没什么用，因为紧急字节与常规字节按照传输的顺序混在
了一起。实际上，Java 接收者并不能区分其是否在接收紧急数据。**
