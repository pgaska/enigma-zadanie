public class TreeProject {
    public static void main(String[] args) {
        Conifer conifer = new Conifer();
        conifer.growBranch();
        conifer.growBranch();
        conifer.grow(4);
        conifer.growCones(20);
        conifer.looseCones(12);
        System.out.println(conifer);

        LeafyTree leafyTree = new LeafyTree();
        leafyTree.growBranch(2);
        leafyTree.grow(10);
        leafyTree.growLeaves(40);
        System.out.println(leafyTree);
        leafyTree.looseLeaves();
        System.out.println(leafyTree);
    }
}
