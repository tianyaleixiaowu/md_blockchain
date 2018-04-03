# md_blockchain
Java区块链平台，基于Springboot开发的区块链平台。

主要有存储模块、网络模块、加密模块等。

该区块链平台属于"链"，非"币"。主要是做联盟链，用来做分布式存储的，不涉及虚拟币。本质上类似于腾讯区块链项目trustsql。

Block内存储的是类Sql语句，各节点通过达成过半同意后即可生成Block，然后全网广播，拉取Block，然后执行Block内的sql语句。

各节点通过执行相同的sql来实现一个同步的sqlite数据库，将来对数据的查询都是直接查询sqlite，性能高于传统的区块链项目。

适用的场景比较多了，但凡能用sql来表示的都可以，像比特币的账单也完全可以用数据库来存储。

如果因为某些原因导致链分叉了，也提供了回滚机制，sql可以回滚。

使用方法：先启动md_blockchain_manager项目，然后修改application.yml里的name、appid和managerUrl和manager项目数据库里的一一对应，作为一个节点启动即可。

可以通过访问localhost:8080/block?content=1来生成一个区块，至少要启动2个节点才行，生成Block时需要除自己外的至少过半同意才行。生成Block后就会发现别的节点也会自动同步自己新生成的Block。

可以通过localhost:8080/sqlite来查看sqlite里存的数据，就是根据Block里的sql语句执行后的结果。



