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
            if(iterations != word.length()){
                currentNode = currentNode.getChildren()[index];
            }
        }

        currentNode.incrementValue();
        wordCount++;
    }

    @Override
    public INode find(String word) {
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
        return super.hashCode();
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
        return super.equals(obj);
    }
}
