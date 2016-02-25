package shapetd.Panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JLabel;
import javax.swing.JPanel;

import shapetd.GameObjects.Enemies.Enemy;

@SuppressWarnings("serial")
public class EnemyInfoPanel extends JPanel {

	private JLabel enemyType;
	private JLabel enemyLevel;
	private JLabel enemyHealth;
	private JLabel enemySpeed;
	private JLabel enemyReward;

	public EnemyInfoPanel() {
		super();
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{153, 205, 162, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gbl_panel);

		JLabel lblEnemyType = new JLabel("Enemy Type            ");
		GridBagConstraints gbc_lblEnemyType = new GridBagConstraints();
		gbc_lblEnemyType.fill = GridBagConstraints.VERTICAL;
		gbc_lblEnemyType.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnemyType.gridx = 1;
		gbc_lblEnemyType.gridy = 0;
		this.add(lblEnemyType, gbc_lblEnemyType);
		enemyType = lblEnemyType;
		enemyType.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		
		JLabel lblEnemyLevel = new JLabel("Level:   ");
		GridBagConstraints gbc_lblCurrentLevel_1 = new GridBagConstraints();
		gbc_lblCurrentLevel_1.fill = GridBagConstraints.BOTH;
		gbc_lblCurrentLevel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentLevel_1.gridx = 0;
		gbc_lblCurrentLevel_1.gridy = 1;
		this.add(lblEnemyLevel, gbc_lblCurrentLevel_1);
		enemyLevel = lblEnemyLevel;
		
		JLabel lblHealth = new JLabel("Health:             ");
		GridBagConstraints gbc_health = new GridBagConstraints();
		gbc_health.fill = GridBagConstraints.BOTH;
		gbc_health.insets = new Insets(0, 0, 5, 5);
		gbc_health.gridx = 0;
		gbc_health.gridy = 2;
		this.add(lblHealth, gbc_health);
		enemyHealth = lblHealth;
		
		JLabel lblEnemySpeed = new JLabel("Speed:        ");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.fill = GridBagConstraints.BOTH;
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.gridx = 0;
		gbc_lblSpeed.gridy = 3;
		this.add(lblEnemySpeed, gbc_lblSpeed);
		enemySpeed = lblEnemySpeed;
		
		JLabel lblEnemyReward = new JLabel("Reward:           ");
		GridBagConstraints gbc_reward = new GridBagConstraints();
		gbc_reward.fill = GridBagConstraints.BOTH;
		gbc_reward.insets = new Insets(0, 0, 5, 5);
		gbc_reward.gridx = 0;
		gbc_reward.gridy = 4;
		this.add(lblEnemyReward, gbc_reward);
		enemyReward = lblEnemyReward;
	}
	
	
	public void updateEnemyPanel(Enemy e) {
		enemyType.setText(e.getType());
		enemyLevel.setText("Level: "+e.getLevel());
		enemyHealth.setText("Health:  "+e.getHealth()+" / "+e.getOriginalHealth());
		
		BigDecimal bd = new BigDecimal(e.getSpeed());
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
		enemySpeed.setText("Speed: "+bd.doubleValue());
		
		enemyReward.setText("Reward: "+e.getReward());
	}
}
