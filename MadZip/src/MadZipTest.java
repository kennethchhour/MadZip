import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.junit.Test;

public class MadZipTest {
  private File testFile = new File("tmp.txt");
  // this test class not working
  // public static void main(String[] args) throws IOException, ClassNotFoundException {
  // // Create a sample text file
  // String text = "This is a test text for MadZip implementation.";
  // Path originalFilePath = Paths.get("sample.txt");
  // Files.write(originalFilePath, text.getBytes());
  //
  // // Compress the sample text file
  // File inputFile = originalFilePath.toFile();
  // File compressedFile = new File("sample.zip");
  // MadZip.zip(inputFile, compressedFile);
  //
  // // Decompress the compressed file
  // File decompressedFile = new File("sample_unzipped.txt");
  // MadZip.unzip(compressedFile, decompressedFile);
  //
  // // Compare the original and decompressed files
  // Path unzippedFilePath = Paths.get("sample_unzipped.txt");
  // boolean filesEqual =
  // Files.readAllBytes(originalFilePath).equals(Files.readAllBytes(unzippedFilePath));
  // System.out.println("Original and unzipped files are equal: " + filesEqual);
  //
  // // Clean up the files
  // Files.delete(originalFilePath);
  // Files.delete(Paths.get("sample.zip"));
  // Files.delete(unzippedFilePath);
  // }

  // public static void main(String[] args) throws IOException {
  @Test
  public void testGetByteFrequency() throws IOException {
    HashMap<Byte, Long> frequencyMap = HuffmanHelper.getByteFrequencies(testFile);
    System.out.println(frequencyMap);
  }

  @Test
  public void testBuildHuffmanTree() throws IOException {
    HashMap<Byte, Long> frequencyMap = HuffmanHelper.getByteFrequencies(testFile);
    HuffmanNode huffmanTree = HuffmanHelper.buildHuffmanTree(frequencyMap);
    huffmanTree.printTree();
  }

  @Test
  public void testTraverseTree() throws IOException {
    HashMap<Byte, Long> frequencyMap = HuffmanHelper.getByteFrequencies(testFile);
    HuffmanNode huffmanTree = HuffmanHelper.buildHuffmanTree(frequencyMap);
    HashMap<Byte, BitSequence> byteToBitSequenceMap =
        HuffmanHelper.createBitSequenceMap(huffmanTree);
    System.out.println(byteToBitSequenceMap);
  }
}
