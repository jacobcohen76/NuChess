package nuchess.ui.root.view;

import java.awt.event.KeyEvent;

import javax.swing.JDialog;

import nuchess.engine.Chessboard;
import nuchess.player.computer.Computer;
import nuchess.player.computer.algorithm.AlphaBeta;
import nuchess.player.computer.algorithm.MiniMax;

public class ComputerConstructorDialog extends JDialog
{
	private static final long serialVersionUID = -241011733866209931L;
	
	private ContentPanel contentPanel;
	private BoardEvaluatorConstructorDialog becd;
	private MoveOrdererConstructorDialog mocd;
	private Computer computer;
	private int side;
	
	protected GameConstructorDialog gcd;
	
	public ComputerConstructorDialog(int side)
	{
		this.side = side;
		contentPanel = new ContentPanel();
		becd = new BoardEvaluatorConstructorDialog();
		mocd = new MoveOrdererConstructorDialog();
		gcd = null;
		
		computer = getComputer();
		
		add(contentPanel);
		pack();
		setResizable(false);
	}
	
	public Computer getComputer()
	{
		String username = contentPanel.getNicknameText();
		Chessboard board = new Chessboard();
		int recurseDepth = contentPanel.getRecurseDepth();
//		long hashSize = contentPanel.getHashSize();
		
		if(contentPanel.isMiniMax())
		{
			computer = new MiniMax(username, board, becd.getBoardEvaluator(), recurseDepth);
		}
		else
		{
			computer = new AlphaBeta(username, board, becd.getBoardEvaluator(), mocd.getMoveOrderer(), recurseDepth);
		}
		
		return computer;
	}
	
	public void setNicknameText(String nicknameText)
	{
		computer.changeUsername(nicknameText);
		contentPanel.setNicknameText(nicknameText);
	}
	
	private void hideDialog()
	{
		setVisible(false);
	}
	
	/**
	 *
	 * @author Jacob Cohen
	 */
	public class ContentPanel extends javax.swing.JPanel {
		
		private static final long serialVersionUID = -5624780098397241968L;
		
		/**
	     * Creates new form NewJPanel
	     */
	    public ContentPanel() {
	        initComponents();
	    }

	    /**
	     * This method is called from within the constructor to initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is always
	     * regenerated by the Form Editor.
	     */
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	    private void initComponents() {
	
	        confirmButton = new javax.swing.JButton();
	        selectionAlgorithmLabel = new javax.swing.JLabel();
	        recurseDepthLabel = new javax.swing.JLabel();
	        selectionAlgorithmComboBox = new javax.swing.JComboBox<>();
	        nicknameLabel = new javax.swing.JLabel();
	        nicknameTextField = new javax.swing.JTextField();
	        hashSizeLabel = new javax.swing.JLabel();
	        recurseDepthTextField = new javax.swing.JTextField();
	        hashSizeTextField = new javax.swing.JTextField();
	        hashSizeUnitComboBox = new javax.swing.JComboBox<>();
	        boardEvaluatorLabel = new javax.swing.JLabel();
	        boardEvaluatorCustomizeButton = new javax.swing.JButton();
	        moveEvaluatorCustomizeButton = new javax.swing.JButton();
	        moveEvaluatorLabel = new javax.swing.JLabel();
	
	        confirmButton.setText("Confirm");
	        confirmButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                confirmButtonActionPerformed(evt);
	            }
	        });
	
	        selectionAlgorithmLabel.setText("Selection Algorithm");
	
	        recurseDepthLabel.setText("Recurse Depth");
	
	        selectionAlgorithmComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AlphaBeta", "MiniMax" }));
	        selectionAlgorithmComboBox.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                selectionAlgorithmComboBoxActionPerformed(evt);
	            }
	        });
	
	        nicknameLabel.setText("Nickname");
	
	        nicknameTextField.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                nicknameTextFieldActionPerformed(evt);
	            }
	        });
	
	        hashSizeLabel.setText("Hash Size");
	
	        recurseDepthTextField.setText("0");
	        recurseDepthTextField.addKeyListener(new java.awt.event.KeyListener()
	        {
				@Override
				public void keyTyped(KeyEvent e)
				{
					if(!isDigit(e.getKeyChar()))
					{
						e.consume();
					}
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					
				}

				@Override
				public void keyReleased(KeyEvent e)
				{
					
				}
	        });
	
	        hashSizeTextField.setText("1024");
	        hashSizeTextField.addKeyListener(new java.awt.event.KeyListener()
	        {
				@Override
				public void keyTyped(KeyEvent e)
				{
					if(!isDigit(e.getKeyChar()))
					{
						e.consume();
					}
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					
				}

				@Override
				public void keyReleased(KeyEvent e)
				{
					
				}
	        });
	
	        hashSizeUnitComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "B", "KB", "MB", "GB" }));
	        hashSizeUnitComboBox.setSelectedIndex(2);
	        hashSizeUnitComboBox.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                hashSizeUnitComboBoxActionPerformed(evt);
	            }
	        });
	
	        boardEvaluatorLabel.setText("Board Evaluator");
	
	        boardEvaluatorCustomizeButton.setText("Customize");
	        boardEvaluatorCustomizeButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                boardEvaluatorCustomizeButtonActionPerformed(evt);
	            }
	        });
	
	        moveEvaluatorCustomizeButton.setText("Customize");
	        moveEvaluatorCustomizeButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                moveEvaluatorCustomizeButtonActionPerformed(evt);
	            }
	        });
	
	        moveEvaluatorLabel.setText("Move Evaluator");
	
	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(confirmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                            .addComponent(boardEvaluatorLabel)
	                            .addComponent(selectionAlgorithmLabel)
	                            .addComponent(moveEvaluatorLabel)
	                            .addComponent(hashSizeLabel)
	                            .addComponent(recurseDepthLabel)
	                            .addComponent(nicknameLabel))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(hashSizeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addComponent(hashSizeUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
	                            .addComponent(selectionAlgorithmComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(boardEvaluatorCustomizeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(moveEvaluatorCustomizeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(recurseDepthTextField)
	                            .addComponent(nicknameTextField, javax.swing.GroupLayout.Alignment.TRAILING))))
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(nicknameLabel)
	                    .addComponent(nicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(recurseDepthLabel)
	                    .addComponent(recurseDepthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(hashSizeLabel)
	                    .addComponent(hashSizeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(hashSizeUnitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(selectionAlgorithmLabel)
	                    .addComponent(selectionAlgorithmComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(boardEvaluatorLabel)
	                    .addComponent(boardEvaluatorCustomizeButton))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(moveEvaluatorLabel)
	                    .addComponent(moveEvaluatorCustomizeButton))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(confirmButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	    }// </editor-fold>                        
	
	    private void nicknameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                  
	        // TODO add your handling code here:
	    }                                                 
	
	    private void hashSizeUnitComboBoxActionPerformed(java.awt.event.ActionEvent evt) {                                                     
	        // TODO add your handling code here:
	    }                                                    
	
	    private void boardEvaluatorCustomizeButtonActionPerformed(java.awt.event.ActionEvent evt) {    
	    	becd.setLocationRelativeTo(this);
	    	becd.setVisible(true);
	    }                                                             
	
	    private void moveEvaluatorCustomizeButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                             
	        // TODO add your handling code here:
	    }                                                            
	
	    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	hideDialog();
	    	gcd.setPlayer(side, getComputer());
	    }                                             
	
	    private void selectionAlgorithmComboBoxActionPerformed(java.awt.event.ActionEvent evt)
	    {                                                           
	        moveEvaluatorCustomizeButton.setEnabled(selectionAlgorithmComboBox.getSelectedIndex() == 0);
	    }                                                          
	
	    private boolean isDigit(char ch)
	    {
	    	return '0' <= ch && ch <= '9';
	    }
	    
	    public String getNicknameText()
	    {
	    	return nicknameTextField.getText();
	    }
	    
	    public int getRecurseDepth()
	    {
	    	String str = recurseDepthTextField.getText();
	    	if(str.equals(""))
	    	{
	    		str = "0";
	    	}
	    	return Integer.valueOf(recurseDepthTextField.getText());
	    }
	    
	    public long getHashSize()
	    {
	    	String str = recurseDepthTextField.getText();
	    	if(str.equals(""))
	    	{
	    		str = "0";
	    	}
	    	return	(long) (Long.valueOf(str) *
	    			Math.pow(1024L, hashSizeUnitComboBox.getSelectedIndex() * 3));
	    }
	    
	    public boolean isMiniMax()
	    {
	    	return selectionAlgorithmComboBox.getItemAt(selectionAlgorithmComboBox.getSelectedIndex()).equals("MiniMax");
	    }
	    
	    public boolean isAlphaBeta()
	    {
	    	return selectionAlgorithmComboBox.getItemAt(selectionAlgorithmComboBox.getSelectedIndex()).equals("AlphaBeta");
	    }
	    
	    public void setNicknameText(String nicknameText)
	    {
	    	nicknameTextField.setText(nicknameText);
	    }
	
	    // Variables declaration - do not modify                     
	    private javax.swing.JButton boardEvaluatorCustomizeButton;
	    private javax.swing.JLabel boardEvaluatorLabel;
	    private javax.swing.JButton confirmButton;
	    private javax.swing.JLabel hashSizeLabel;
	    private javax.swing.JTextField hashSizeTextField;
	    private javax.swing.JComboBox<String> hashSizeUnitComboBox;
	    private javax.swing.JButton moveEvaluatorCustomizeButton;
	    private javax.swing.JLabel moveEvaluatorLabel;
	    private javax.swing.JLabel nicknameLabel;
	    private javax.swing.JTextField nicknameTextField;
	    private javax.swing.JLabel recurseDepthLabel;
	    private javax.swing.JTextField recurseDepthTextField;
	    private javax.swing.JComboBox<String> selectionAlgorithmComboBox;
	    private javax.swing.JLabel selectionAlgorithmLabel;
	    // End of variables declaration                   
	}

}
