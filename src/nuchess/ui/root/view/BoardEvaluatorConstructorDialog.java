package nuchess.ui.root.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.DropMode;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import nuchess.player.computer.boardeval.BoardEvaluator;
import nuchess.player.computer.boardeval.InCheck;
import nuchess.player.computer.boardeval.BoardFeature;
import nuchess.player.computer.boardeval.Mate;
import nuchess.player.computer.boardeval.Mobility;
import nuchess.player.computer.boardeval.NumChecks;
import nuchess.player.computer.boardeval.PseudoLegalMobility;
import nuchess.player.computer.boardeval.RelativeBishopMaterial;
import nuchess.player.computer.boardeval.RelativeKnightMaterial;
import nuchess.player.computer.boardeval.RelativePawnMaterial;
import nuchess.player.computer.boardeval.RelativeQueenMaterial;
import nuchess.player.computer.boardeval.RelativeRookMaterial;

public class BoardEvaluatorConstructorDialog extends JDialog
{
	private static final long serialVersionUID = 135112890706736156L;
	
	private WeightDecider weightDecider;
	
	public BoardEvaluatorConstructorDialog()
	{
		weightDecider = new WeightDecider();
		add(weightDecider);
		pack();
		setResizable(false);
	}
	
	public BoardEvaluator getBoardEvaluator()
	{
		BoardFeature[] factors = weightDecider.getFeatures();
		int[] weights = weightDecider.getWeights();
		int n = weightDecider.getNumRows();
		return new BoardEvaluator(factors, weights, n);
	}
	
	/**
	 *
	 * @author Jacob Cohen
	 */
	public class WeightDecider extends javax.swing.JPanel
	{
		private static final long serialVersionUID = -8332408797014422954L;
		
		private DefaultTableModel tableModel;
		private ArrayList<BoardFeature> featuresList;
		private AbstractListModel<BoardFeature> listModel;
		
	    /**
	     * Creates new form WeightDecider
	     */
	    public WeightDecider()
	    {
	    	initFeaturesList();
            tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Feature", "Weight" })
	    	{
	    		private static final long serialVersionUID = -8225338543361272365L;
				
	    		public boolean isCellEditable(int row, int column)
	    		{
	    			return column == 0 ? false : super.isCellEditable(row, column);
	    		}
	    	};
	    	
	    	listModel = new AbstractListModel<BoardFeature>()
	    	{	            
				private static final long serialVersionUID = -6199430926115424087L;

				public int getSize()
	            {
	            	return featuresList.size();
	            }
	            
	            public BoardFeature getElementAt(int i)
	            {
	            	return featuresList.get(i);
	            }
	    	};
	        initComponents();
	    }
	    
	    private void initFeaturesList()
	    {
	    	featuresList = new ArrayList<BoardFeature>();
	    	featuresList.add(new Mate());
	    	featuresList.add(new InCheck());
	    	featuresList.add(new NumChecks());
	    	featuresList.add(new RelativePawnMaterial());
	    	featuresList.add(new RelativeKnightMaterial());
	    	featuresList.add(new RelativeBishopMaterial());
	    	featuresList.add(new RelativeRookMaterial());
	    	featuresList.add(new RelativeQueenMaterial());
	    	featuresList.add(new PseudoLegalMobility());
	    	featuresList.add(new Mobility());
	    }
	    
	    @SuppressWarnings("rawtypes")
		public BoardFeature[] getFeatures()
	    {
	    	Vector<Vector> dataVector = tableModel.getDataVector();
	    	BoardFeature[] features = new BoardFeature[dataVector.size()];
	    	for(int i = 0; i < dataVector.size(); i++)
	    	{
	    		features[i] = (BoardFeature) dataVector.get(i).get(0);
	    	}
	    	return features;
	    }
	    
	    @SuppressWarnings("rawtypes")
		public int[] getWeights()
	    {
	    	Vector<Vector> dataVector = tableModel.getDataVector();
	    	int[] weights = new int[dataVector.size()];
	    	for(int i = 0; i < dataVector.size(); i++)
	    	{
	    		weights[i] = Integer.valueOf(dataVector.get(i).get(0).toString());
	    	}
	    	return weights;
	    }
	    
	    public int getNumRows()
	    {
	    	return tableModel.getDataVector().size();
	    }

	    /**
	     * This method is called from within the constructor to initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is always
	     * regenerated by the Form Editor.
	     */
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	    private void initComponents()
	    {
	        weightsListScrollPane = new javax.swing.JScrollPane();
	        weightsList = new javax.swing.JList<>();
	        weightsTableScrollPane = new javax.swing.JScrollPane();
	        weightsTable = new javax.swing.JTable();

	        weightsList.setFocusable(false);
	        weightsList.setDragEnabled(true);
	    	weightsList.setDropMode(DropMode.ON);	    		
	        weightsList.setModel(listModel);
	        weightsList.addListSelectionListener(new ListSelectionListener()
	        {
				@Override
				public void valueChanged(ListSelectionEvent e)
				{
					weightsListValueChanged(e);
				}
	        });
	        weightsListScrollPane.setViewportView(weightsList);

	        weightsTable.setModel(tableModel);
	        weightsTableScrollPane.setViewportView(weightsTable);
	        weightsTable.getTableHeader().setReorderingAllowed(false);
	        weightsTable.setColumnSelectionAllowed(false);
	        weightsTable.setRowSelectionAllowed(false);
	        weightsTable.addKeyListener(new KeyListener()
	        {
				@Override
				public void keyTyped(KeyEvent e)
				{
					
				}

				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyCode() == KeyEvent.VK_DELETE)
					{
						featuresList.add((BoardFeature) tableModel.getValueAt(weightsTable.getSelectedRow(), 0));
						tableModel.removeRow(weightsTable.getSelectedRow());
						weightsTable.revalidate();
						weightsList.repaint();
					}
				}

				@Override
				public void keyReleased(KeyEvent e)
				{
					
				}
	        });
	        
	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(weightsListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(weightsTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(weightsListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
	            .addComponent(weightsTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
	        );
	    }// </editor-fold>        
	    
	    private void weightsListValueChanged(ListSelectionEvent e)
	    {
	    	if(0 <= weightsList.getSelectedIndex())
	    	{
		    	tableModel.addRow(new Object[] { weightsList.getSelectedValue(), 0 } );
		    	featuresList.remove(weightsList.getSelectedIndex());
		    	weightsList.clearSelection();
		    	weightsList.repaint();
	    	}
	    }
	    
	    // Variables declaration - do not modify                     
	    private javax.swing.JList<BoardFeature> weightsList;
	    private javax.swing.JScrollPane weightsListScrollPane;
	    private javax.swing.JTable weightsTable;
	    private javax.swing.JScrollPane weightsTableScrollPane;
	    // End of variables declaration                   
	}
}
