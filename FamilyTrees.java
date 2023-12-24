import java.util.*;
import java.io.*;

class Tree {
    String name;
    Tree left;
    Tree right;

    public Tree(String n) {
        this.name = n;
        this.left = null;
        this.right = null;
    }
}
public class FamilyTrees {
    private Tree root;

    public void readFamily(Scanner input) {
        String ancestor = input.next();
        int nSons = input.nextInt();

        root = new Tree(ancestor);

        for (int j = 0; j < nSons; j++) {
            readNode(root, input);
        }
    }

    public void readNode(Tree pNode, Scanner input) {
        String cName = input.next();
        int nSons = input.nextInt();

        Tree curr = new Tree(cName);

        if (pNode.left == null) {
            pNode.left = curr;
        } else {
            pNode.right = curr;
        }

        for (int j = 0; j < nSons; j++) {
            readNode(curr, input);
        }
    }

    public String readFather(String pName) {
        Tree father = getNode(root, pName);
        if (father != null  && father != root) {
            Tree f = readParent(root, father);
            return f.name;
        }
        return "The father is not found";
    }

    public List<String> readSons(String pName) {
        List<String> Nsons = new ArrayList<>();
        Tree son = getNode(root, pName);
        if (son != null  && son != root && son.left != null) {
            Tree s = son.left;
            while (s != null)  {
                Nsons.add(s.name);
                s = s.right;
            }
        }
        return Nsons;
    }


    public List<String> readBrothers(String pName) {
        List<String> Nbrothers = new ArrayList<>();
        Tree node = getNode(root, pName);
        if (node != null && node != root) {
            Tree adult = readParent(root, node);
            Tree sibling = adult.left;
            while (sibling != null) {
                if (sibling != node) {
                    Nbrothers.add(sibling.name);
                }
                sibling = sibling.right;
            }
        }
        return Nbrothers;
    }

    public String readOldestBrother(String pName) {
        List<String> Nbrothers = readBrothers(pName);
        if (!Nbrothers.isEmpty()) {
            return Nbrothers.get(0);
        }
        return "The oldest brother is not found";
    }

    public String readYoungestBrother(String pName) {
        List<String> Nbrothers = readBrothers(pName);
        if (!Nbrothers.isEmpty()) {
            return Nbrothers.get(Nbrothers.size() - 1);
        }
        return "The youngest brother is not found";
    }

    public String readOldestSon(String pName) {
        Tree node = getNode(root, pName);
        if (node != null && node.left != null) {
            return node.left.name;
        }
        return "The oldest son is not found";
    }

    public String readYoungestSon(String pName) {
        Tree node = getNode(root, pName);
        if (node != null && node.left != null) {
            Tree youngest = node.left;
            while (youngest.right != null) {
                youngest = youngest.right;
            }
            return youngest.name;
        }
        return "The youngest son is not found";
    }

    public List<String> readUncles(String pName) {
        List <String> nUncles = new ArrayList<>();
        Tree node = getNode(root, pName);
        if (node != null && node != root) {
            Tree parentNode = readParent(root, node);
            Tree grandparentNode = readParent(root, node);
            Tree uncleNode = grandparentNode.left;
            while (uncleNode != null) {
                if (uncleNode != parentNode) {
                    nUncles.add(uncleNode.name);
                }
                uncleNode = uncleNode.right;
            }
        }
        return nUncles;
    }

    public String readGrandparent(String pName) {
        Tree node = getNode(root, pName);
        if (node != null && node != root) {
            Tree parentNode = readParent(root, node);
            if (parentNode != null && parentNode != root) {
                Tree grandparentNode = readParent(root, node);
                if (grandparentNode != null) {
                    return grandparentNode.name;
                }
            }
        }
        return "The grandfather is not found";
    }

    private Tree getNode(Tree curr, String pName) {
        if (curr == null) {
            return null;
        }

        if (curr.name.equals(pName)) {
            return curr;
        }

        Tree leftNode = getNode(curr.left, pName);

        if (leftNode != null) {
            return leftNode;
        }
        return getNode(curr.right, pName);
    }

    private Tree readParent(Tree curr, Tree childNode) {
        if (curr == null || childNode == null) {
            return null;
        }
        if (curr.left == childNode || curr.right == childNode) {
            return curr;
        }
        Tree leftNode = readParent(curr.left, childNode);
        if (leftNode != null) {
            return leftNode;
        }
        return readParent(curr.right, childNode);
    }
    public static void main(String[] args) throws Exception {
        File file = new File ("input.txt");
        Scanner input = new Scanner(file);
        PrintWriter output = new PrintWriter("output.txt");

        int numberOfTrees = 1;

        while (input.hasNext()) {
            output.println("Family Tree #" + numberOfTrees);
            FamilyTrees family = new FamilyTrees();
            family.readFamily(input);

            output.println("Original Tree:");
            printTree(output, family.root, "");

            output.println("\nQuestions and Answers:");

            String pName = "Bob";

            output.println("1) Who is the father of " + pName + "?");
            output.println(family.readFather(pName));

            output.println("2) Who are all the sons of " + pName + "?");
            List <String> nSons = family.readSons(pName);
            if (nSons.isEmpty()) {
                output.println("There are no sons found");
            } else {
                for (String s : nSons) {
                    output.println(s);
                }
            }

            output.println("4) Who is the oldest brother of " + pName + "?");
            output.println(family.readOldestBrother(pName));

            output.println("5) Who is the youngest brother of p?");
            output.println(family.readYoungestBrother(pName));

            output.println("6) Who is the oldest son of " + pName + "?");
            output.println(family.readOldestSon(pName));

            output.println("7) Who is the youngest son of " + pName + "?");
            output.println(family.readYoungestSon("John"));

            output.println("8) Who are the uncles of " + pName + "?");
            List<String> nUncles = family.readUncles(pName);
            if (nUncles.isEmpty()) {
                output.println("There are no uncles found");
            } else {
                for (String u : nUncles) {
                    output.println(u);
                }
            }

            output.println("9) Who is the grandfather of " + pName + "?");
            output.println(family.readGrandparent(pName));

            numberOfTrees++;
            output.println();
            }

            input.close();
            output.close();
        }

        private static void printTree(PrintWriter output, Tree curr, String space) {
            if (curr != null) {
                output.println(space + curr.name);
                printTree(output, curr.left, space + " ");
                printTree(output, curr.right, space + " ");
            }
        }
    }
