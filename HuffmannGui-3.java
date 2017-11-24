package huffmannCode;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import javax.swing.*;


 
public class HuffmannGui implements ActionListener {
	JTextArea t2;
	JComboBox<File>jcmb;
	int length = 0;
	JLabel jlbl,jlbl1, jlbl2;
	abstract class HuffmanTree implements Comparable<HuffmanTree> {
	    public final int frequency;
	    public HuffmanTree(int freq) { frequency = freq; }
	 
	    public int compareTo(HuffmanTree tree) {
	        return frequency - tree.frequency;
	    }
	}
	 
	class HuffmanLeaf extends HuffmanTree {
	    public final char value;
	 
	    public HuffmanLeaf(int freq, char val) {
	        super(freq);
	        value = val;
	    }
	}
	 
	class HuffmanNode extends HuffmanTree {
	    public final HuffmanTree left, right;
	 
	    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
	        super(l.frequency + r.frequency);
	        left = l;
	        right = r;
	    }
	}
	static String[] codes = new String[256];
    
    public HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
 
        assert trees.size() > 0;
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            codes[(int)leaf.value] = prefix.toString();
 
        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
    public void actionPerformed(ActionEvent e){
    	String test = "";
    	DataInputStream dis;
    	int len1 = 0;
    	try {
    		dis = new DataInputStream(new FileInputStream((File)jcmb.getSelectedItem()));
    		len1 = dis.available();
			while(dis.available()!=0) {
				test = test + dis.readLine()+'\n';
			}
		}
    	catch(FileNotFoundException f) {}
    	catch(IOException ef) {}
	    int[] charFreqs = new int[256];
	    for (char c : test.toCharArray())
	        charFreqs[c]++;
	    HuffmanTree tree = buildTree(charFreqs);
	    printCodes(tree, new StringBuffer());
	    String finalStr = "";
	    int i=0;
	    while(i < test.length()) {
	    		finalStr+=codes[(int)test.charAt(i)];
	    		i++;
	    }
    	t2.setText(finalStr);
    	length = finalStr.length();
    	jlbl1.setText("The size of the document is : "+ Integer.toString(length) + " bits");
    	jlbl2.setText("Compression Ratio"+ " : " + Double.toString(len1*16.0/length) );
    }
    
	FileReader fr;
	JPanel panel = new JPanel();
	private File file = new File("C:/Users/Hp/Desktop/City.txt");
	HuffmannGui(){
		JFrame jfrm = new JFrame("Huffmann Encoder");
		jfrm.setLayout(null);
		jfrm.setSize(1200, 1000);
		jfrm.setResizable(false);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);
		
		jlbl = new JLabel("Please select a file:");
		jlbl.setBounds(400, 20, 400 , 40);
		panel.add(jlbl);
		
		jlbl = new JLabel("The selected document:");
		jlbl.setBounds(40, 120, 400 , 40);
		panel.add(jlbl);
		
		jlbl = new JLabel("The Encoded Document:");
		jlbl.setBounds(620, 120, 400 , 40);
		panel.add(jlbl);
		
		File folder = new File("C:/Users/Hp/Desktop/");
		File[] list = folder.listFiles();
		File[] list1 = new File[5];
		int j=0;
		for(File i:list) {
			if(i.isFile()&&i.getName().endsWith(".txt")) {
				list1[j] = i;
				j++;
			}
		}
		
		JTextArea t1;
		
		t1 = new JTextArea();
		t1.setBounds(40, 180, 500, 600);
		t1.setLineWrap(true);
		t1.setWrapStyleWord(true);
		jcmb = new JComboBox<File>(list1);
		jcmb.setBounds(400, 60, 400, 40);
		panel.add(jcmb);
		jcmb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				t1.setText("");
				File f = (File)jcmb.getSelectedItem();
				file = f;
				String line;
				String s;
				s = file.getAbsolutePath();
				jlbl.setText("The size of the document is : " + file.length()*16 + " bits");
				try {
					fr = new FileReader(s);
				} catch (FileNotFoundException e) {
				}
				BufferedReader reader = new BufferedReader(fr);
				try {
					while((line = reader.readLine())!=null) {
						t1.append(line + "\n");
					}
				} catch (IOException e) {
				}
			}
		});
		JScrollPane scrl;
		
		scrl = new JScrollPane(t1);
		panel.add(scrl, BorderLayout.CENTER);
		panel.add(t1);
		
		JButton jbtn1 = new JButton("Convert");
		jbtn1.setBounds(460, 810, 80, 30);
		panel.add(jbtn1);
		
		jbtn1.addActionListener(this);
		
		jlbl = new JLabel("The size of the document is : ");
		jlbl.setBounds(300, 850, 250, 30);
		panel.add(jlbl);
		
		t2 = new JTextArea();
		t2.setBounds(620, 180, 500, 600);
		t2.setLineWrap(true);
		t2.setWrapStyleWord(true);
		t2.add(scrl);
		panel.add(t2);
		
		jlbl1 = new JLabel("The size of the document is : ");
		jlbl1.setBounds(880, 850, 250, 30);
		panel.add(jlbl1);
		
		jlbl2 = new JLabel("Compression Ratio"+ " : ");
		jlbl2.setBounds(880, 880, 250, 30);
		panel.add(jlbl2);
		
		jfrm.setContentPane(panel);
		jfrm.setLocationRelativeTo(null);
		jfrm.setVisible(true);
	}
}
