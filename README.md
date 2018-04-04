# md_blockchain
Java区块链平台，基于Springboot开发的区块链平台。区块链qq交流群737858576，刚建的群，一起学习区块链平台开发。公司要开发区块链，原本是想着使用以太坊开发个合约或者是使用个第三方平台来做，后来发现都不符合业务需求，公司要求自己开发区块链平台，仅联盟链即可。所以于3月开始研发，历时一月发布了这个版本。基本功能有了，但细节尚不到位。希望高手不吝赐教，来做一个区块链平台项目，适合各种场景，分布式存储，不仅仅是账本。

主要有存储模块、网络模块、加密模块、区块解析入库等。

该区块链平台属于"链"，非"币"。主要是做联盟链，用来做分布式存储的，不涉及虚拟币。本质上类似于腾讯区块链项目trustsql。

Block内存储的是类Sql语句，各节点通过达成过半同意后即可生成Block，然后全网广播，拉取Block，然后执行Block内的sql语句。

各节点通过执行相同的sql来实现一个同步的sqlite数据库，将来对数据的查询都是直接查询sqlite，性能高于传统的区块链项目。

适用的场景比较多了，但凡能用sql来表示的都可以，像比特币的账单也完全可以用数据库来存储。

如果因为某些原因导致链分叉了，也提供了回滚机制，sql可以回滚。

使用方法：先启动md_blockchain_manager项目（https://gitee.com/tianyalei/md_blockchain_manager），然后修改application
.yml里的name、appid和managerUrl和manager项目数据库里的一一对应，作为一个节点启动即可。

可以通过访问localhost:8080/block?content=1来生成一个区块，至少要启动2个节点才行，生成Block时需要除自己外的至少过半同意才行。生成Block后就会发现别的节点也会自动同步自己新生成的Block。

可以通过localhost:8080/sqlite来查看sqlite里存的数据，就是根据Block里的sql语句执行后的结果。

Block存储采用的是key-value数据库rocksDB，了解比特币的知道，比特币用的是levelDB，都是类似的东西。

区块里存放的是类似于sql的语句，如ADD（增删改） tableName（表名）ID（主键） JSON（该记录的json）。

节点可以根据区块里的这些语句，进而将数据落地，我这里用的内嵌数据库sqlite，当然也支持mysql等数据库，用的jpa，操作sqlite和mysql其实没有任何区别。

网络层，采用的是各节点互相长连接、断线重连，然后维持心跳包。任何一个节点都可以生成Block，不同于比特币的挖矿和其他的需要先选举，他们都是只能由特定的节点来生成区块。我这里可以由任何节点来生成，只要过半同意即可。
生成Block时需要全网广播，等待其他节点的校验（校验格式、hash、签名、和table的权限），校验通过后，过半同意了，就可以构建Block并广播全网，通知各节点更新Block。

我把项目部署到docker里了，共启动4个节点，如图：
![输入图片说明](https://gitee.com/uploads/images/2018/0404/105151_c8931604_303698.png "1.png")

manager就是md_blockchain_manager项目，主要功能就是提供联盟链内各节点ip
![输入图片说明](https://gitee.com/uploads/images/2018/0404/105409_5e24cb3a_303698.png "1.png")

四个节点ip都写死了，都启动后，它们会相互全部连接起来，并维持住长连接和心跳包。
![输入图片说明](https://gitee.com/uploads/images/2018/0404/105748_bc6896d8_303698.png "1.png")

我调用一下block项目的生成区块接口，http://ip:port/block?content=1

![输入图片说明](https://gitee.com/uploads/images/2018/0404/105945_9e7f946f_303698.png "1.png")

别的节点会是这样，收到block项目请求生成区块的请求、并开始校验，回复是否同意
![输入图片说明](https://gitee.com/uploads/images/2018/0404/110142_cae21d7f_303698.png "1.png")

当block项目收到过半的同意后，就开始生成区块，并广播给其他节点自己的新区块，其他节点开始拉取新块，校验通过了则更新到本地。

这个生成区块的接口是写好用来测试的，正常走的流程是调用instuction接口，先生产符合自己需求的指令，然后组合多个指令，调用BlockController里的生成区块接口。




