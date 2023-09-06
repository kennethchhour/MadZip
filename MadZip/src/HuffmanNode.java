/**
 * This work complies with the JMU Honor Code.
 *
 * <p>Node class for the Huffman Tree.
 * 
 * @author kenneth chhour
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
  private Byte value;
  private Long frequency;
  private HuffmanNode left;
  private HuffmanNode right;

  /**
   * Constructor for creating a leaf HuffmanNode with a value and frequency.
   *
   * @param value The byte value represented by the HuffmanNode.
   * @param frequency The frequency of the byte value.
   */
  public HuffmanNode(Byte value, Long frequency) {
    this.value = value;
    this.frequency = frequency;
  }

  /**
   * Constructor for creating a non-leaf HuffmanNode with two child nodes.
   *
   * @param left The left child node.
   * @param right The right child node.
   */
  public HuffmanNode(HuffmanNode left, HuffmanNode right) {
    this.frequency = left.getFrequency() + right.getFrequency();
    this.left = left;
    this.right = right;
  }

  /**
   * Gets the byte value of this node.
   * 
   * @return byte value
   */
  public Byte getValue() {
    return value;
  }

  /**
   * Gets the frequency value of this node.
   * 
   * @return frequency value
   */
  public Long getFrequency() {
    return frequency;
  }

  /**
   * Gets the left node of this node.
   * 
   * @return left node
   */
  public HuffmanNode getLeft() {
    return left;
  }

  /**
   * Gets the right node of this node.
   * 
   * @return right node
   */
  public HuffmanNode getRight() {
    return right;
  }

  /**
   * Checks if this node is a leaf.
   * 
   * @return true or false
   */
  public boolean isLeaf() {
    return left == null && right == null;
  }

  /**
   * Gets the lowest byte value from the tree.
   * 
   * @return the lowest byte value
   */
  private byte getLowestByte() {
    if (isLeaf()) {
      return value;
    }

    byte leftLow = left.getLowestByte();
    byte rightLow = right.getLowestByte();

    if (leftLow < rightLow) {
      return leftLow;
    } else {
      return rightLow;
    }
  }

  @Override
  public int compareTo(HuffmanNode other) {
    int comparison = Long.compare(this.frequency, other.frequency);

    if (comparison != 0) {
      return comparison;
    } else {
      return Byte.compare(this.getLowestByte(), other.getLowestByte());
    }
  }

  // for testing purposes
  @Override
  public String toString() {
    if (isLeaf()) {
      return "Leaf Node (" + value + ", " + frequency + ")";
    } else {
      return "Internal Node (" + frequency + ")";
    }
  }

  public void printTree() {
    printTreeHelper(this, 0);
  }

  private void printTreeHelper(HuffmanNode node, int depth) {
    if (node != null) {
      for (int i = 0; i < depth; i++) {
        System.out.print("  ");
      }
      System.out.println(node);

      printTreeHelper(node.left, depth + 1);
      printTreeHelper(node.right, depth + 1);
    }
  }
}
