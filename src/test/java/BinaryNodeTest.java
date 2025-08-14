import org.alvio.bst.model.BinaryNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BinaryNodeTest<T extends Comparable<T>> {
    private BinaryNode<Integer> root;
    private BinaryNode<Integer> leftChild;
    private BinaryNode<Integer> rightChild;

    @BeforeEach
    void setUp() {
        leftChild = new BinaryNode<>(1);
        rightChild = new BinaryNode<>(3);
        root = new BinaryNode<>(2, leftChild, rightChild);
    }

    // Core Functionality Tests
    @Test
    void testConstructorWithValue() {
        BinaryNode<Integer> node = new BinaryNode<>(5);
        assertEquals(5, node.getValue());
        assertNull(node.getLeft());
        assertNull(node.getRight());
    }

    @Test
    void testConstructorWithChildren() {
        assertEquals(2, root.getValue());
        assertEquals(1, root.getLeft().getValue());
        assertEquals(3, root.getRight().getValue());
    }

    // JSON Conversion Tests
    @Test
    void testToJsonCompact() {
        String expected = "{\"value\":2,\"left\":{\"value\":1,\"left\":null,\"right\":null},\"right\":{\"value\":3,\"left\":null,\"right\":null}}";
        assertEquals(expected, root.toJson());
    }

    @Test
    void testToJson_ProducesFlatJson() {
        // Setup
        BinaryNode<Integer> root = new BinaryNode<>(2,
                new BinaryNode<>(1),
                new BinaryNode<>(3));

        // Act
        String json = root.toJson();

        // Assert
        String expected = "{\"value\":2,\"left\":{\"value\":1,\"left\":null,\"right\":null},\"right\":{\"value\":3,\"left\":null,\"right\":null}}";
        assertEquals(expected, json);
    }

    // Edge Cases
    @Test
    void testNullChildrenInJson() {
        BinaryNode<Integer> node = new BinaryNode<>(5);
        String json = node.toJson();
        assertEquals("{\"value\":5,\"left\":null,\"right\":null}", json);
    }

    @Test
    void testStringNodeSerialization_PatternMatch() {
        BinaryNode<String> node = new BinaryNode<>("test",
                new BinaryNode<>("left"),
                new BinaryNode<>("right"));

        String json = node.toJson();

        assertAll(
                () -> assertTrue(json.contains("\"value\":\"test\""), "Missing root value"),
                () -> assertTrue(json.contains("\"left\":{\"value\":\"left\""), "Missing left node"),
                () -> assertTrue(json.contains("\"right\":{\"value\":\"right\""), "Missing right node")
        );
    }
}