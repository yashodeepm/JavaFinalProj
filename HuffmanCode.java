package huffmannCode;

import javax.swing.SwingUtilities;
 
public class HuffmanCode{
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new HuffmannGui();
			}
		});
    }
}
