package huffmannCode;

import javax.swing.SwingUtilities;
 
public class HuffmanCode{
    public static void main(String[] args){
    		
        //System.out.println(finalStr.substring(0, finalStr.length()-codes[(int)'\n'].length()));
        SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new HuffmannGui();
			}
		});
    }
}