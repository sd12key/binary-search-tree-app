import org.alvio.bst.model.BinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    private BinarySearchTree<Integer> bst;

    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree<>();
    }

    // Insertion Tests
    @Test
    void insert_IntoEmptyTree_SetsRoot() {
        bst.insert(5);
        assertEquals(5, bst.getRoot().getValue());
    }

    @Test
    void insert_LesserValue_GoesLeft() {
        bst.insert(5);
        bst.insert(3);
        assertEquals(3, bst.getRoot().getLeft().getValue());
    }

    @Test
    void insert_GreaterValue_GoesRight() {
        bst.insert(5);
        bst.insert(7);
        assertEquals(7, bst.getRoot().getRight().getValue());
    }

    @Test
    void insert_EqualValue_GoesLeft() {
        bst.insert(5);
        bst.insert(5);
        assertEquals(5, bst.getRoot().getLeft().getValue());
    }

    // JSON Serialization Tests
    @Test
    void toJson_EmptyTree_ReturnsNull() {
        assertEquals("null", bst.toJson());
    }

    @Test
    void toJson_SingleNode_ReturnsValidJson() {
        bst.insert(5);
        assertEquals("{\"root\":{\"value\":5,\"left\":null,\"right\":null}}", bst.toJson());
    }

    @Test
    void toJson_MultipleNodes_ReturnsValidJson() {
        bst.insert(2);
        bst.insert(1);
        bst.insert(3);
        String expected = "{\"root\":{\"value\":2,\"left\":{\"value\":1,\"left\":null,\"right\":null},\"right\":{\"value\":3,\"left\":null,\"right\":null}}}";
        assertEquals(expected, bst.toJson());
    }

    // Search Tests
    @Test
    void search_ExistingValue_ReturnsNode() {
        bst.insert(5);
        bst.insert(3);
        assertEquals(3, bst.search(bst.getRoot(), 3).getValue());
    }

    @Test
    void search_NonExistingValue_ReturnsNull() {
        bst.insert(5);
        assertNull(bst.search(bst.getRoot(), 10));
    }

    // Deletion Tests
    @Test
    void deleteNode_LeafNode_RemovesCorrectly() {
        bst.insert(5);
        bst.insert(3);
        bst.deleteNode(bst.getRoot(), 3);
        assertNull(bst.getRoot().getLeft());
    }

    @Test
    void deleteNode_WithOneChild_RemovesCorrectly() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(1);
        bst.deleteNode(bst.getRoot(), 3);
        assertEquals(1, bst.getRoot().getLeft().getValue());
    }

    @Test
    void deleteBST_ClearsTree() {
        bst.insert(5);
        bst.deleteBST();
        assertNull(bst.getRoot());
    }

    // Traversal Tests
    @Test
    void inOrder_PrintsCorrectSequence() {
        bst.insert(4);
        bst.insert(2);
        bst.insert(6);
        bst.insert(1);
        bst.insert(3);
        bst.insert(5);
        bst.insert(7);
        // Should print: 1 2 3 4 5 6 7
        bst.inOrder(bst.getRoot());
    }

    @Test
    void levelOrder_PrintsCorrectSequence() {
        bst.insert(4);
        bst.insert(2);
        bst.insert(6);
        bst.insert(1);
        bst.insert(3);
        bst.insert(5);
        bst.insert(7);
        // Should print: 4 2 6 1 3 5 7
        bst.levelOrder();
    }
}