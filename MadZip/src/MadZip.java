import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * This work complies with the JMU Honor Code.
 *
 * <p>Utility class to compress and decompress files.
 * 
 * @author kenneth chhour
 */
public class MadZip {
  /**
   * Compresses the input file and writes the compressed data to the output file.
   *
   * @param inputFile The file to be compressed.
   * @param outputFile The file to write the compressed data to.
   * @throws IOException If an I/O error occurs.
   */
  public static void zip(File inputFile, File outputFile) throws IOException {
    // check for empty files
    if (inputFile.length() == 0) {
      try (FileOutputStream fos = new FileOutputStream(outputFile)) {
        return;
      }
    }

    HashMap<Byte, Long> frequencyMap = HuffmanHelper.getByteFrequencies(inputFile);
    HuffmanNode huffmanTree = HuffmanHelper.buildHuffmanTree(frequencyMap);
    HashMap<Byte, BitSequence> byteToBitMap = HuffmanHelper.createBitSequenceMap(huffmanTree);

    BitSequence encodedBit = new BitSequence();
    try (FileInputStream fis = new FileInputStream(inputFile)) {
      int data;
      while ((data = fis.read()) != -1) {
        encodedBit.appendBits(byteToBitMap.get((byte) data));
      }
    }

    HuffmanSave huffmanSave = new HuffmanSave(encodedBit, frequencyMap);
    try (FileOutputStream fos = new FileOutputStream(outputFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(huffmanSave);
    }
  }

  /**
   * Decompresses the input file and writes the decompressed data to the output file.
   *
   * @param inputFile The file to be decompressed.
   * @param outputFile The file to write the decompressed data to.
   * @throws IOException If an I/O error occurs.
   */
  public static void unzip(File inputFile, File outputFile)
      throws IOException, ClassNotFoundException {
    // check for empty files
    if (inputFile.length() == 0) {
      try (FileOutputStream fos = new FileOutputStream(outputFile)) {
        return;
      }
    }

    HuffmanSave huffmanSave;

    try (FileInputStream fis = new FileInputStream(inputFile);
        ObjectInputStream ois = new ObjectInputStream(fis)) {
      huffmanSave = (HuffmanSave) ois.readObject();
    }

    HashMap<Byte, Long> frequencies = huffmanSave.getFrequencies();
    // check for one byte files
    if (frequencies.size() == 1) {
      byte oneByte = frequencies.keySet().iterator().next();
      long count = frequencies.get(oneByte);

      try (FileOutputStream fos = new FileOutputStream(outputFile)) {
        for (long i = 0; i < count; i++) {
          fos.write(oneByte);
        }
        return;
      }
    }

    HuffmanNode huffmanTree = HuffmanHelper.buildHuffmanTree(huffmanSave.getFrequencies());
    BitSequence encodedBit = huffmanSave.getEncoding();

    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
      HuffmanNode currentNode = huffmanTree;
      for (int i = 0; i < encodedBit.length(); i++) {
        int bit = encodedBit.getBit(i);
        currentNode = bit == 0 ? currentNode.getLeft() : currentNode.getRight();

        if (currentNode.isLeaf()) {
          fos.write(currentNode.getValue());
          currentNode = huffmanTree;
        }
      }
    }
  }
}
