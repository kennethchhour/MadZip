import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This work complies with the JMU Honor Code.
 *
 * <p>Helper class for MadZip.
 * 
 * @author kenneth chhour
 */
public class HuffmanHelper {
  /**
   * Reads a file and returns a map of byte frequencies.
   *
   * @param file The file to read the byte frequencies from.
   * @return A map of byte values to their corresponding frequencies.
   * @throws IOException If an I/O error occurs.
   */
  public static HashMap<Byte, Long> getByteFrequencies(File file) throws IOException {
    HashMap<Byte, Long> frequencyMap = new HashMap<>();
    try (FileInputStream fis = new FileInputStream(file)) {
      int data;
      while ((data = fis.read()) != -1) {
        byte currentByte = (byte) data;
        frequencyMap.put(currentByte, frequencyMap.getOrDefault(currentByte, 0L) + 1);
      }
    }
    return frequencyMap;
  }

  /**
   * Creates a map of byte values to their corresponding BitSequence Huffman codes.
   *
   * @param frequencyMap Map of Huffman codes.
   * @return A map of byte values to their corresponding BitSequence Huffman codes.
   */
  public static HuffmanNode buildHuffmanTree(HashMap<Byte, Long> frequencyMap) {
    PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>();

    for (Map.Entry<Byte, Long> entry : frequencyMap.entrySet()) {
      minHeap.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
    }

    while (minHeap.size() > 1) {
      HuffmanNode leftNode = minHeap.poll();
      HuffmanNode rightNode = minHeap.poll();

      HuffmanNode parentNode = new HuffmanNode(leftNode, rightNode);
      minHeap.offer(parentNode);
    }

    return minHeap.poll();
  }

  /**
   * A recursive helper method to traverse the Huffman tree and create the byte-to-BitSequence map.
   *
   * @param huffmanTree Huffman tree to be traversed.
   */
  public static HashMap<Byte, BitSequence> createBitSequenceMap(HuffmanNode huffmanTree) {
    HashMap<Byte, BitSequence> byteToBitSequenceMap = new HashMap<>();
    traverseTree(huffmanTree, byteToBitSequenceMap, new BitSequence());
    return byteToBitSequenceMap;
  }

  /**
   * A recursive helper method to traverse the Huffman tree and create the byte-to-BitSequence map.
   *
   * @param node The current node in the tree traversal.
   * @param byteToBitSequenceMap The map of byte values to their corresponding BitSequence Huffman
   *        codes.
   * @param currentPath The current BitSequence code generated during traversal.
   */
  private static void traverseTree(HuffmanNode node,
      HashMap<Byte, BitSequence> byteToBitSequenceMap, BitSequence currentPath) {
    if (node.isLeaf()) {
      byteToBitSequenceMap.put(node.getValue(), currentPath);
    } else {
      BitSequence leftPath = new BitSequence(currentPath);
      leftPath.appendBit(0);
      traverseTree(node.getLeft(), byteToBitSequenceMap, leftPath);

      BitSequence rightPath = new BitSequence(currentPath);
      rightPath.appendBit(1);
      traverseTree(node.getRight(), byteToBitSequenceMap, rightPath);
    }
  }
}
