package searchfilters;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.jupiter.api.Test;
class treeFilterTest {

	@Test
	void treeFilterCanAddAndRemoveNode() {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		
		root.add(new DefaultMutableTreeNode("1"));
		root.add(new DefaultMutableTreeNode("2"));
		root.add(new DefaultMutableTreeNode("3"));
		root.add(new DefaultMutableTreeNode("4"));
		root.add(new DefaultMutableTreeNode("5"));
		
		DefaultMutableTreeNode child1 = root.getFirstLeaf();
		
		child1.add(new DefaultMutableTreeNode("a"));
		child1.add(new DefaultMutableTreeNode("b"));
		child1.add(new DefaultMutableTreeNode("c"));
		child1.add(new DefaultMutableTreeNode("d"));
		
		DefaultMutableTreeNode childa = child1.getFirstLeaf();
	
		JTree tree = new JTree(root);
		
		String old = printTree(root);
		
		treeFilter filter = new treeFilter(tree) {

			@Override
			public boolean analyseNode(DefaultMutableTreeNode node) {
				return false;
			}};
		
		System.out.println(filter);
		
		filter.removeFromTree(childa);
		
		System.out.println(filter);
		
		filter.removeFromTree(child1);
		
		System.out.println(filter);
		
		printTree(root);
		
		filter.PopToTree();
		
		System.out.println(filter);
		
		filter.PopToTree();
		
		System.out.println(filter);
		
		String newOne = printTree(root);
	
		assertEquals(newOne, old);
	}
	
	@Test
	void treeFilterFiltreCorrectly() {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		
		root.add(new DefaultMutableTreeNode("1"));
		root.add(new DefaultMutableTreeNode("2"));
		root.add(new DefaultMutableTreeNode("3"));
		root.add(new DefaultMutableTreeNode("4"));
		root.add(new DefaultMutableTreeNode("5"));
		
		DefaultMutableTreeNode child1 = root.getFirstLeaf();
		
		child1.add(new DefaultMutableTreeNode("a"));
		child1.add(new DefaultMutableTreeNode("b"));
		child1.add(new DefaultMutableTreeNode("c"));
		child1.add(new DefaultMutableTreeNode("d"));
		
		
		JTree tree = new JTree(root);
		
		System.out.println("before filtrage:");
		String old = printTree(root);
		
		treeFilter filter = new treeFilter(tree) {

			@Override
			public boolean analyseNode(DefaultMutableTreeNode node) {
				if(node.toString().equals("b") || node.toString().equals("4")) {
					return true;
				}
				
				return false;
			}};
			
		
		filter.filtreTree();
		
		System.out.println("after filtrage:");
		printTree(root);
		
		filter.unfiltreTree();
		System.out.println("after recovery:");
		String newOne = printTree(root);
		
		assertEquals(newOne, old);
		
		
	}
	
	
	private String printTree(DefaultMutableTreeNode node) {
		
		String temp = "";
		
		for(int i = 0 ; i < node.getChildCount(); i++) {
			
			temp += (node.getChildAt(i));
			
			System.out.println(node.getChildAt(i));
			temp += printTree((DefaultMutableTreeNode)node.getChildAt(i));
			
			
		}
		
		return temp;
		
	}
	
	
	
	
	
	

}
