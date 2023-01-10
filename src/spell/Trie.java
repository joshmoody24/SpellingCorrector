package spell;

public class Trie implements ITrie {

    private INode root;
    private int wordCount;
    private int nodeCount;

    public Trie(){
        root = new Node();
        wordCount = 0;
        nodeCount = 1;
    }

    @Override
    public void add(String word) {
        INode currentNode = root;

        // used because the last iteration of the loop shouldn't change currentNode
        int iterations = 0;

        for (char c : word.toLowerCase().toCharArray()){
            int index = c - 'a';
            if(currentNode.getChildren()[index] == null){
                currentNode.getChildren()[index] = new Node();
                nodeCount++;
            }
            iterations++;
            currentNode = currentNode.getChildren()[index];
        }

        currentNode.incrementValue();
        if(currentNode.getValue() == 1) wordCount++;
    }

    @Override
    public INode find(String word) {
        INode currentNode = root;
        for (char c : word.toLowerCase().toCharArray()) {
            int index = c - 'a';
            if(currentNode.getChildren()[index] != null){
                currentNode = currentNode.getChildren()[index];
            }
            else{
                return null;
            }
        }
        if(currentNode.getValue() > 0) return currentNode;
        return null;
    }

    @Override
    public int getWordCount() {
        return wordCount;
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public int hashCode() {
        int hash = wordCount * nodeCount;
        // mix in the indexes of the root node's non-null children
        for(int i = 0; i < root.getChildren().length; i++){
            if(root.getChildren()[i] != null){
                hash *= i + 1;
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder currentWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(root, currentWord, output);
        return output.toString();
    }

    private void toString_Helper(INode node, StringBuilder currentWord, StringBuilder output){
        if(node.getValue() > 0){
            // append node's word to output
            output.append(currentWord.toString());
            output.append("\n");
        }

        for(int i = 0; i < node.getChildren().length; i++){
            INode child = node.getChildren()[i];
            if(child != null){
                // append the child's character to it
                char childChar = (char)('a' + i);
                currentWord.append(childChar);
                toString_Helper(child, currentWord, output);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != this.getClass()) return false;
        Trie other = (Trie)obj;
        if(other.getNodeCount() != this.getNodeCount()) return false;
        if(other.getWordCount() != this.getWordCount()) return false;

        // traverse the trees to determine equality the hard way
        return equals_Helper(other.root, root);
    }

    private boolean equals_Helper(INode n1, INode n2){
        // compare n1 and n2 to see if they are the same
        if(n1 == null && n2 == null) return true;
        if(n1 == null && n2 != null) return false;
        if(n1 != null && n2 == null) return false;
        if(n1.getValue() != n2.getValue()) return false;

        // not null children in same indices?
        if(n1.getChildren().length != n2.getChildren().length) return false;
        for(int i = 0; i < n1.getChildren().length; i++){
            if(n1.getChildren()[i] == null && n2.getChildren()[i] != null) return false;
            if(n2.getChildren()[i] == null && n1.getChildren()[i] != null) return false;
        }
        // recurse on children and compare child subtrees
        // assume they are equal at first
        boolean children_equal = true;
        for(int i = 0; i < n1.getChildren().length; i++){
            if(equals_Helper(n1.getChildren()[i], n2.getChildren()[i]) == false) {
                children_equal = false;
                break;
            }
        }
        return children_equal;
    }
}
