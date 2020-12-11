import java.util.function.Predicate;

import tester.Tester;

class Deque<T> {
  Sentinel<T> header;

  // takes zero arguments and initializes the header to a new Sentinel<T>
  Deque() {
    this.header = new Sentinel<T>();
  }

  // convenience constructor which takes a particular Sentinel value to use
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // returns the number of Nodes in this Deque
  int size() {
    return this.header.countNode();
  }

  // adds the given value to the head of this deque
  void addAtHead(T value) {
    this.header.add(value);
  }

  // adds the given value to the tail of this deque
  void addAtTail(T value) {
    this.header.prev.add(value);
  }

  // removes the first value of this deque
  T removeFromHead() {
    // return this.header.removeFromHeadSent();
    return this.header.next.remove();
  }

  // removes the last value of this deque
  T removeFromTail() {
    return this.header.prev.remove();
  }

  // produces first node in the deck for which the given pred is true
  ANode<T> find(Predicate<T> pred) {
    return this.header.next.find(pred);
  }

  // removes the given node from the deck
  void removeNode(ANode<T> node) {
    node.remove();
  }

} ////////////////////////////// end of deque

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // counts Nodes connected to this, excluding this Node
  // invoked on the Sentinel header
  int countNode() {
    return this.next.countNodeHelp();
  }

  // returns the first node in the deck for which the given pred returns true
  abstract ANode<T> find(Predicate<T> pred);

  // helper for countNodes
  // counts Nodes until it reaches Sentinel
  abstract int countNodeHelp();

  // places the given value in the next position of this node
  abstract void add(T value);

  // removes this node and returns the data contained in the removed node
  abstract T remove();

} //////////////////////////// end of ANode

class Sentinel<T> extends ANode<T> {
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // places the given value in the next position of this node
  public void add(T value) {
    ANode<T> oldnext = this.next;
    this.next = new Node<T>(value, oldnext, this);
  }

  // helper for countNodes
  public int countNodeHelp() {
    return 0;
  }

  //removes this node and returns the data contained in the removed node
  public T remove() {
    throw new RuntimeException("cannot remove from an empty list!");
  }

  //returns this as the pred did not pass for any nodes
  public ANode<T> find(Predicate<T> pred) {
    return this;
  }

} ////////////////////////////////// end of sentinel

class Node<T> extends ANode<T> {
  T data;

  // takes just a value of type T, initializes the data field,
  // and then initializes next and prev to null.
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  // convenience constructor should take a value of type T
  // and two ANode<T> nodes, initialize the data field to the given value
  Node(T data, ANode<T> next, ANode<T> prev) {
    if (prev == null || next == null) {
      throw new IllegalArgumentException("either of the given nodes is null");
    }

    this.data = data;
    this.next = next;

    // next node pointing backwards to this
    next.prev = this;
    this.prev = prev;
    // prev node pointing towards to this
    prev.next = this;
  }

  // helper for countNodes
  public int countNodeHelp() {
    return 1 + this.next.countNodeHelp();
  }

  // removes this node from the list, returns the data contained in the node
  T remove() {
    ANode<T> oldprev = this.prev;
    ANode<T> oldnext = this.next;
    this.prev.next = oldnext;
    this.next.prev = oldprev;
    return this.data;
  }

  // returns the node that passes the given pred
  ANode<T> find(Predicate<T> pred) {
    if (pred.test(this.data)) {
      return this;
    }
    else {
      return this.next.find(pred);
    }
  }

  // adds the given value to the list
  void add(T value) {
    ANode<T> oldnext = this.next;
    this.next = new Node<T>(value, oldnext, this);
  }

} ////////////////////////////////////////// end of node

// examples for Deque
class ExamplesDeque {

  // all of these begin as mt deques
  Deque<String> deque0;
  Deque<String> deque0copy;
  Deque<String> mtdeque;

  Deque<String> deque1;
  Deque<String> deque2;
  Deque<String> deque3;
  Deque<String> deque4;

  Sentinel<String> sentinel1;
  Sentinel<String> sentinel2;
  Sentinel<String> sentinel3;
  Sentinel<String> sentinel4;

  ANode<String> node1;
  ANode<String> node2;
  ANode<String> node3;
  ANode<String> node4;

  ANode<String> nodeD;
  ANode<String> nodeA;
  ANode<String> nodeZ;
  ANode<String> nodeE;
  ANode<String> nodeH;

  ANode<String> nodeAA;
  ANode<String> nodeBB;
  ANode<String> nodeCC;

  ANode<String> nodeWW;
  ANode<String> nodeXX;
  ANode<String> nodeYY;

  // RESULTS OF CALLING ADDTOHEAD("th") ON DEQUE0
  Sentinel<String> sentinel0x = new Sentinel<String>();
  ANode<String> node0x = new Node<String>("th", sentinel0x, sentinel0x);
  Deque<String> deque0x = new Deque<String>(sentinel0x);

  // RESULTS OF CALLING ADDTOHEAD("h) ON DEQUE1
  Sentinel<String> sentinel1x = new Sentinel<String>();
  ANode<String> node5x = new Node<String>("d", sentinel1x, sentinel1x);
  ANode<String> node1x = new Node<String>("abc", sentinel1x, node5x);
  ANode<String> node2x = new Node<String>("bcd", sentinel1x, node1x);
  ANode<String> node3x = new Node<String>("cde", sentinel1x, node2x);
  ANode<String> node4x = new Node<String>("def", sentinel1x, node3x);
  Deque<String> deque1x = new Deque<String>(sentinel1x);

  // RESULTS OF CALLING ADDTOTAIL("o") ON DEQUE2
  Sentinel<String> sentinel2x = new Sentinel<String>();
  ANode<String> nodeDx = new Node<String>("d", sentinel2x, sentinel2x);
  ANode<String> nodeAx = new Node<String>("a", sentinel2x, nodeDx);
  ANode<String> nodeZx = new Node<String>("z", sentinel2x, nodeAx);
  ANode<String> nodeEx = new Node<String>("e", sentinel2x, nodeZx);
  ANode<String> nodeHx = new Node<String>("h", sentinel2x, nodeEx);
  ANode<String> nodeOx = new Node<String>("o", sentinel2x, nodeHx);
  Deque<String> deque2x = new Deque<String>(sentinel2x);

  // RESULTS OF CALLING REMOVEFROMHEAD ON DEQUE 3`
  Sentinel<String> sentinel3x = new Sentinel<String>();
  ANode<String> nodeBBx = new Node<String>("BB", sentinel3x, sentinel3x);
  ANode<String> nodeCCx = new Node<String>("CC", sentinel3x, nodeBBx);
  Deque<String> deque3x = new Deque<String>(sentinel3x);

  // RESULTS OF CALLING REMOVEFROMTAIL ON DEQUE 4`
  Sentinel<String> sentinel4x = new Sentinel<String>();
  ANode<String> nodeWWx = new Node<String>("WW", sentinel4x, sentinel4x);
  ANode<String> nodeXXx = new Node<String>("XX", sentinel4x, nodeWWx);
  Deque<String> deque4x = new Deque<String>(sentinel4x);

  class Preds implements Predicate<String> {
    String ex;

    Preds(String ex) {
      this.ex = ex;
    }

    @Override
    public boolean test(String t) {
      return t.equals(this.ex);
    }
  }

  public void initData() {

    this.deque0 = new Deque<String>();
    this.deque0copy = new Deque<String>();
    this.mtdeque = new Deque<String>();

    this.sentinel1 = new Sentinel<String>();

    this.node1 = new Node<String>("abc", sentinel1, sentinel1);
    this.node2 = new Node<String>("bcd", sentinel1, node1);
    this.node3 = new Node<String>("cde", sentinel1, node2);
    this.node4 = new Node<String>("def", sentinel1, node3);
    this.deque1 = new Deque<String>(sentinel1);

    this.sentinel2 = new Sentinel<String>();
    this.nodeD = new Node<String>("d", sentinel2, sentinel2);
    this.nodeA = new Node<String>("a", sentinel2, nodeD);
    this.nodeZ = new Node<String>("z", sentinel2, nodeA);
    this.nodeE = new Node<String>("e", sentinel2, nodeZ);
    this.nodeH = new Node<String>("h", sentinel2, nodeE);
    this.deque2 = new Deque<String>(sentinel2);

    this.sentinel3 = new Sentinel<String>();
    this.nodeAA = new Node<String>("AA", sentinel3, sentinel3);
    this.nodeBB = new Node<String>("BB", sentinel3, nodeAA);
    this.nodeCC = new Node<String>("CC", sentinel3, nodeBB);
    this.deque3 = new Deque<String>(sentinel3);

    this.sentinel4 = new Sentinel<String>();
    this.nodeWW = new Node<String>("WW", sentinel4, sentinel4);
    this.nodeXX = new Node<String>("XX", sentinel4, nodeWW);
    this.nodeYY = new Node<String>("YY", sentinel4, nodeXX);
    this.deque4 = new Deque<String>(sentinel4);
  }

  // test for size method
  void testDeque(Tester t) {

    this.initData();

    // tests find
    t.checkExpect(this.deque4.find(new Preds("WW")), nodeWW);
    t.checkExpect(this.deque4.find(new Preds("WW")), this.nodeWW);
    t.checkExpect(this.deque4.find(new Preds("notfound")), this.sentinel4);
    t.checkExpect(new Preds("abc").test("abc"), true);

    t.checkExpect(this.sentinel2.next, nodeD);
    t.checkExpect(this.node1.prev, sentinel1);
    t.checkExpect(this.sentinel1.prev, node4);

    // tests for size
    t.checkExpect(this.deque0.size(), 0);
    t.checkExpect(this.deque1.size(), 4);
    t.checkExpect(this.deque2.size(), 5);

    // test addAtHead
    this.deque1.addAtHead("d");
    t.checkExpect(this.deque1, deque1x);
    this.deque0copy.addAtHead("th");
    t.checkExpect(this.deque0copy, deque0x);

    // tests remove
    t.checkExpect(deque0x.size(), 1); // comfirming there is 1 node in the list
    this.deque0x.header.next.remove(); // should make an empty list since only 1 node
    t.checkExpect(deque0x, new Deque<String>(new Sentinel<String>()));

    // test add
    t.checkExpect(mtdeque.size(), 0); // starts empty
    this.mtdeque.header.add("hi"); // add a node
    t.checkExpect(mtdeque.size(), 1); // size is 1
    // should both point to the same node as there is only 1
    t.checkExpect(mtdeque.header.next, mtdeque.header.prev); 

    //test addAtTail
    this.deque2.addAtTail("o");
    t.checkExpect(this.deque2, deque2x);
    this.deque0x.addAtTail("th"); // 0x is currently empty
    // returns the same as when add to head was called on an empty w same data
    t.checkExpect(this.deque0x, deque0copy); 
                                              

    //testsremovenode
    t.checkExpect(this.deque0copy.size(), 1); // currently has 1 node
    // given the "th" node that was just added to remove
    this.deque0copy.removeNode(this.deque0copy.header.next); 
                                                             
    t.checkExpect(this.deque0copy.size(), 0); // no nodes after its been removed
    t.checkExpect(this.deque0copy.header.next, this.deque0copy.header); // header points to itself

    //testremove fromhead
    t.checkExpect(this.deque3.removeFromHead(), "AA");
    t.checkExpect(this.deque3, deque3x);
    t.checkException(new RuntimeException("cannot remove from an empty list!"), this.deque0,
        "removeFromHead");

    //testremove fromtail
    t.checkExpect(this.deque4.removeFromTail(), "YY");
    t.checkExpect(this.deque4, deque4x);
    t.checkException(new RuntimeException("cannot remove from an empty list!"), this.deque0,
        "removeFromTail");

  }

}
