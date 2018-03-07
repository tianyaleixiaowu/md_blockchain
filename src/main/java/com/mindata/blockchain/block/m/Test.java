package com.mindata.blockchain.block.m;

import java.util.List;

/**
 * @author wuweifeng wrote on 2018/3/6.
 */
public class Test {
    public static void main(String[] args) {

        MerkleTree merkleTree = new MerkleTree();
        MerkleNode merkleNode0 = new MerkleNode(MerkleHash.create("a"));
        MerkleNode merkleNode1 = new MerkleNode(MerkleHash.create("b"));
        MerkleNode merkleNode2 = new MerkleNode(MerkleHash.create("c"));
        MerkleNode merkleNode3 = new MerkleNode(MerkleHash.create("d"));

        merkleTree.appendLeaf(merkleNode0);
        merkleTree.appendLeaf(merkleNode1);
        merkleTree.appendLeaf(merkleNode2);
        merkleTree.appendLeaf(merkleNode3);
        merkleTree.buildTree();
        System.out.println(merkleTree.getRoot().getHash());
        List<MerkleProofHash> hashes = merkleTree.auditProof(MerkleHash.create("a"));
    }
}
