package nuchess.ui.root.view;

import javax.swing.JDialog;

import nuchess.engine.Color;
import nuchess.player.Player;
import nuchess.ui.root.control.RootControl;

public class GameConstructorDialog extends JDialog
{
	private static final long serialVersionUID = 2580607268440803679L;
	
	private Player whitePlayer, blackPlayer;
	private ContentPanel contentPanel;
	private ComputerConstructorDialog wccd, bccd;
	private HumanConstructorDialog whcd, bhcd;
	
	public RootControl control;
	
	public GameConstructorDialog()
	{
		contentPanel = new ContentPanel();
		
		wccd = new ComputerConstructorDialog(Color.WHITE);
		bccd = new ComputerConstructorDialog(Color.BLACK);
		whcd = new HumanConstructorDialog(Color.WHITE);
		bhcd = new HumanConstructorDialog(Color.BLACK);
		
		wccd.gcd = this;
		bccd.gcd = this;
		whcd.gcd = this;
		bhcd.gcd = this;
		
		whcd.setResizable(false);
		bhcd.setResizable(false);
		
		wccd.setTitle("White - Construct a New Computer Player");
		bccd.setTitle("Black - Construct a New Computer Player");
		whcd.setTitle("White - Construct a New Human Player");
		bhcd.setTitle("Black - Construct a New Human Player");
		
		wccd.setNicknameText("Default White Computer");
		bccd.setNicknameText("Default Black Computer");
		whcd.setUsernameText("Default White Human");
		bhcd.setUsernameText("Default Black Human");
		
		control = null;
		
		add(contentPanel);
		
		pack();
		setResizable(false);
		setTitle("Constructing a New Game");
		
		whitePlayer = contentPanel.getWhitePlayer();
		blackPlayer = contentPanel.getBlackPlayer();
		
		contentPanel.setWhitePlayerUsername(whitePlayer.getUsername());
		contentPanel.setBlackPlayerUsername(blackPlayer.getUsername());
	}
	
	public void hideDialog()
	{
		setVisible(false);
	}
	
	public void setPlayer(int side, Player player)
	{
		if(side == Color.WHITE)		setWhitePlayer(player);
		else						setBlackPlayer(player);
	}
	
	public void setWhitePlayer(Player player)
	{
		whitePlayer = player;
		contentPanel.setWhitePlayerUsername(player.getUsername());
	}
	
	public void setBlackPlayer(Player player)
	{
		blackPlayer = player;
		contentPanel.setBlackPlayerUsername(player.getUsername());
	}
	
	/**
	 *
	 * @author Jacob Cohen
	 */
	private class ContentPanel extends javax.swing.JPanel {
		
		private static final long serialVersionUID = 7934062575202116558L;
		
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

	        playButton = new javax.swing.JButton();
	        whiteLabel = new javax.swing.JLabel();
	        blackLabel = new javax.swing.JLabel();
	        whiteCustomizePlayerButton = new javax.swing.JButton();
	        blackCustomizePlayerButton = new javax.swing.JButton();
	        whitePlayerTypeComboBox = new javax.swing.JComboBox<>();
	        blackPlayerTypeComboBox = new javax.swing.JComboBox<>();
	        FENLabel = new javax.swing.JLabel();
	        FENTextField = new javax.swing.JTextField();
	        whitePlayerUsernameLabel = new javax.swing.JLabel();
	        blackPlayerUsernameLabel = new javax.swing.JLabel();

	        playButton.setText("Play");
	        playButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                playButtonActionPerformed(evt);
	            }
	        });

	        whiteLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	        whiteLabel.setText("WHITE PLAYER:");

	        blackLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	        blackLabel.setText("BLACK PLAYER:");

	        whiteCustomizePlayerButton.setText("Customize");
	        whiteCustomizePlayerButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                whiteCustomizePlayerButtonActionPerformed(evt);
	            }
	        });

	        blackCustomizePlayerButton.setText("Customize");
	        blackCustomizePlayerButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                blackCustomizePlayerButtonActionPerformed(evt);
	            }
	        });

	        whitePlayerTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Human", "Computer" }));
	        whitePlayerTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                whitePlayerTypeComboBoxActionPerformed(evt);
	            }
	        });

	        blackPlayerTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Human", "Computer" }));
	        blackPlayerTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                blackPlayerTypeComboBoxActionPerformed(evt);
	            }
	        });

	        FENLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
	        FENLabel.setText("FEN");

	        FENTextField.setText("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

	        whitePlayerUsernameLabel.setText("Default White Player");

	        blackPlayerUsernameLabel.setText("Default Black Player");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(playButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
	                            .addComponent(FENLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(blackLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                            .addComponent(whiteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(FENTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
	                            .addGroup(layout.createSequentialGroup()
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                                    .addComponent(whitePlayerUsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                    .addComponent(blackPlayerUsernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                                        .addComponent(blackCustomizePlayerButton)
	                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                        .addComponent(blackPlayerTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                                        .addComponent(whiteCustomizePlayerButton)
	                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                                        .addComponent(whitePlayerTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(whitePlayerTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(whiteCustomizePlayerButton)
	                    .addComponent(whiteLabel)
	                    .addComponent(whitePlayerUsernameLabel))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(blackPlayerTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(blackCustomizePlayerButton)
	                    .addComponent(blackLabel)
	                    .addComponent(blackPlayerUsernameLabel))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(FENLabel)
	                    .addComponent(FENTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(playButton)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	    }// </editor-fold>                        

	    private void whitePlayerTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	if(whitePlayerTypeComboBox.getSelectedIndex() == 0)
	    	{
	    		setWhitePlayer(whcd.getHuman());
	    	}
	    	else
	    	{
	    		setWhitePlayer(wccd.getComputer());
	    	}
	    }
	    
	    private void blackPlayerTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	if(blackPlayerTypeComboBox.getSelectedIndex() == 0)
	    	{
	    		setBlackPlayer(bhcd.getHuman());
	    	}
	    	else
	    	{
	    		setBlackPlayer(bccd.getComputer());
	    	}
	    }                                                       

	    private void playButtonActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	hideDialog();
	    	control.openNewGameTab(whitePlayer, blackPlayer, FENTextField.getText());
	    }                                          

	    private void blackCustomizePlayerButtonActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	if(blackPlayerTypeComboBox.getSelectedIndex() == 0)
	    	{
	    		bhcd.setLocationRelativeTo(this);
	    		bhcd.setVisible(true);
	    		bccd.setVisible(false);
	    	}
	    	else
	    	{
	    		bccd.setLocationRelativeTo(this);
	    		bccd.setVisible(true);
	    		bhcd.setVisible(false);
	    	}
	    }                                                          

	    private void whiteCustomizePlayerButtonActionPerformed(java.awt.event.ActionEvent evt)
	    {
	    	if(whitePlayerTypeComboBox.getSelectedIndex() == 0)
	    	{
	    		whcd.setLocationRelativeTo(this);
	    		whcd.setVisible(true);
	    		wccd.setVisible(false);
	    	}
	    	else
	    	{
	    		wccd.setLocationRelativeTo(this);
	    		wccd.setVisible(true);
	    		whcd.setVisible(false);
	    	}
	    }
	    
	    public void setWhitePlayerUsername(String username)
	    {
	    	whitePlayerUsernameLabel.setText(username);
	    }

	    public void setBlackPlayerUsername(String username)
	    {
	    	blackPlayerUsernameLabel.setText(username);
	    }
	    
	    public Player getWhitePlayer()
	    {
	    	if(whitePlayerTypeComboBox.getSelectedIndex() == 0)
	    	{
	    		return whcd.getHuman();
	    	}
	    	else
	    	{
	    		return wccd.getComputer();
	    	}
	    }
	    
	    public Player getBlackPlayer()
	    {
	    	if(blackPlayerTypeComboBox.getSelectedIndex() == 0)
	    	{
	    		return bhcd.getHuman();
	    	}
	    	else
	    	{
	    		return bccd.getComputer();
	    	}
	    }

	    // Variables declaration - do not modify                     
	    private javax.swing.JLabel FENLabel;
	    private javax.swing.JTextField FENTextField;
	    private javax.swing.JButton blackCustomizePlayerButton;
	    private javax.swing.JLabel blackLabel;
	    private javax.swing.JComboBox<String> blackPlayerTypeComboBox;
	    private javax.swing.JLabel blackPlayerUsernameLabel;
	    private javax.swing.JButton playButton;
	    private javax.swing.JButton whiteCustomizePlayerButton;
	    private javax.swing.JLabel whiteLabel;
	    private javax.swing.JComboBox<String> whitePlayerTypeComboBox;
	    private javax.swing.JLabel whitePlayerUsernameLabel;
	    // End of variables declaration                   
	}
}
