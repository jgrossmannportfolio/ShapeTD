package shapetd.Panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shapetd.Game;
import shapetd.GameObjects.Towers.Tower;

import java.awt.Component;

import javax.swing.Box;

@SuppressWarnings("serial")
public class TowerInfoPanel extends JPanel {

	private JLabel towerType;
	private JLabel towerLevel;
	private JLabel towerNextLevel;
	private JLabel upgradeCost;
	private JLabel towerDamage;
	private JLabel towerNextDamage;
	private JButton upgrade;
	private JLabel towerSpeed;
	private JLabel towerNextSpeed;
	private JLabel sellAmount;
	private JLabel towerRange;
	private JLabel towerNextRange;
	private JButton sell;
	private JLabel splashDamage;
	private JLabel splashRange;
	private JLabel nextSplashDamage;
	private JLabel nextSplashRange;

	public TowerInfoPanel() {
		super();
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{172, 160, 162, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gbl_panel);
		
		JLabel lblTowerType = new JLabel("Tower Type");
		GridBagConstraints gbc_lblTowerType = new GridBagConstraints();
		gbc_lblTowerType.fill = GridBagConstraints.BOTH;
		gbc_lblTowerType.insets = new Insets(0, 0, 5, 5);
		gbc_lblTowerType.gridx = 1;
		gbc_lblTowerType.gridy = 0;
		this.add(lblTowerType, gbc_lblTowerType);
		towerType = lblTowerType;
		towerType.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblCurrentLevel_1 = new JLabel("Current Level: ");
		GridBagConstraints gbc_lblCurrentLevel_1 = new GridBagConstraints();
		gbc_lblCurrentLevel_1.fill = GridBagConstraints.BOTH;
		gbc_lblCurrentLevel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentLevel_1.gridx = 0;
		gbc_lblCurrentLevel_1.gridy = 2;
		this.add(lblCurrentLevel_1, gbc_lblCurrentLevel_1);
		towerLevel = lblCurrentLevel_1;
		
		JLabel lblNextLevel = new JLabel("Next Level: ");
		GridBagConstraints gbc_lblNextLevel = new GridBagConstraints();
		gbc_lblNextLevel.fill = GridBagConstraints.BOTH;
		gbc_lblNextLevel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNextLevel.gridx = 1;
		gbc_lblNextLevel.gridy = 2;
		this.add(lblNextLevel, gbc_lblNextLevel);
		towerNextLevel = lblNextLevel;
		
		JLabel lblDamage = new JLabel("Damage: ");
		GridBagConstraints gbc_lblDamage = new GridBagConstraints();
		gbc_lblDamage.fill = GridBagConstraints.BOTH;
		gbc_lblDamage.insets = new Insets(0, 0, 5, 5);
		gbc_lblDamage.gridx = 0;
		gbc_lblDamage.gridy = 3;
		this.add(lblDamage, gbc_lblDamage);
		towerDamage = lblDamage;
		
		JLabel lblDamage_1 = new JLabel("Damage: ");
		GridBagConstraints gbc_lblDamage_1 = new GridBagConstraints();
		gbc_lblDamage_1.fill = GridBagConstraints.BOTH;
		gbc_lblDamage_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDamage_1.gridx = 1;
		gbc_lblDamage_1.gridy = 3;
		this.add(lblDamage_1, gbc_lblDamage_1);
		towerNextDamage = lblDamage_1;
		
		JLabel lblUpgradeCost = new JLabel("Upgrade Cost: ");
		GridBagConstraints gbc_lblUpgradeCost = new GridBagConstraints();
		gbc_lblUpgradeCost.fill = GridBagConstraints.VERTICAL;
		gbc_lblUpgradeCost.insets = new Insets(0, 0, 5, 0);
		gbc_lblUpgradeCost.gridx = 2;
		gbc_lblUpgradeCost.gridy = 3;
		this.add(lblUpgradeCost, gbc_lblUpgradeCost);
		upgradeCost = lblUpgradeCost;
		
		JLabel lblSpeed = new JLabel("Speed: ");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.fill = GridBagConstraints.BOTH;
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.gridx = 0;
		gbc_lblSpeed.gridy = 4;
		this.add(lblSpeed, gbc_lblSpeed);
		towerSpeed = lblSpeed;
		
		JLabel lblSpeed_1 = new JLabel("Speed: ");
		GridBagConstraints gbc_lblSpeed_1 = new GridBagConstraints();
		gbc_lblSpeed_1.fill = GridBagConstraints.BOTH;
		gbc_lblSpeed_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed_1.gridx = 1;
		gbc_lblSpeed_1.gridy = 4;
		this.add(lblSpeed_1, gbc_lblSpeed_1);
		towerNextSpeed = lblSpeed_1;
		
		JButton btnUpgrade = new JButton("Upgrade");
		GridBagConstraints gbc_btnUpgrade = new GridBagConstraints();
		gbc_btnUpgrade.fill = GridBagConstraints.VERTICAL;
		gbc_btnUpgrade.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpgrade.gridx = 2;
		gbc_btnUpgrade.gridy = 4;
		this.add(btnUpgrade, gbc_btnUpgrade);
		upgrade = btnUpgrade;
		upgrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.getInstance().upgradeSelectedTower();
			}
		});
		
		JLabel lblRange = new JLabel("Range: ");
		GridBagConstraints gbc_lblRange = new GridBagConstraints();
		gbc_lblRange.fill = GridBagConstraints.BOTH;
		gbc_lblRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblRange.gridx = 0;
		gbc_lblRange.gridy = 5;
		this.add(lblRange, gbc_lblRange);
		towerRange = lblRange;
		
		JLabel lblRange_1 = new JLabel("Range: ");
		GridBagConstraints gbc_lblRange_1 = new GridBagConstraints();
		gbc_lblRange_1.fill = GridBagConstraints.BOTH;
		gbc_lblRange_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblRange_1.gridx = 1;
		gbc_lblRange_1.gridy = 5;
		this.add(lblRange_1, gbc_lblRange_1);
		towerNextRange = lblRange_1;
		
		JLabel lblSplashDamage = new JLabel("Splash Damage: ");
		GridBagConstraints gbc_lblSplashDamage = new GridBagConstraints();
		gbc_lblSplashDamage.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSplashDamage.insets = new Insets(0, 0, 5, 5);
		gbc_lblSplashDamage.gridx = 0;
		gbc_lblSplashDamage.gridy = 6;
		add(lblSplashDamage, gbc_lblSplashDamage);
		splashDamage = lblSplashDamage;
		
		JLabel lblSplashDamage_1 = new JLabel("Splash Damage: ");
		GridBagConstraints gbc_lblSplashDamage_1 = new GridBagConstraints();
		gbc_lblSplashDamage_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSplashDamage_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSplashDamage_1.gridx = 1;
		gbc_lblSplashDamage_1.gridy = 6;
		add(lblSplashDamage_1, gbc_lblSplashDamage_1);
		nextSplashDamage = lblSplashDamage_1;
		
		JLabel lblSellAmount = new JLabel("Sell Amount: ");
		GridBagConstraints gbc_lblSellAmount = new GridBagConstraints();
		gbc_lblSellAmount.fill = GridBagConstraints.VERTICAL;
		gbc_lblSellAmount.insets = new Insets(0, 0, 5, 0);
		gbc_lblSellAmount.gridx = 2;
		gbc_lblSellAmount.gridy = 6;
		this.add(lblSellAmount, gbc_lblSellAmount);
		sellAmount = lblSellAmount;
		
		JLabel lblSplashRange = new JLabel("Splash Range: ");
		GridBagConstraints gbc_lblSplashRange = new GridBagConstraints();
		gbc_lblSplashRange.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSplashRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblSplashRange.gridx = 0;
		gbc_lblSplashRange.gridy = 7;
		add(lblSplashRange, gbc_lblSplashRange);
		splashRange = lblSplashRange;
		
		JLabel lblSplashRange_1 = new JLabel("Splash Range: ");
		GridBagConstraints gbc_lblSplashRange_1 = new GridBagConstraints();
		gbc_lblSplashRange_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSplashRange_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblSplashRange_1.gridx = 1;
		gbc_lblSplashRange_1.gridy = 7;
		add(lblSplashRange_1, gbc_lblSplashRange_1);
		nextSplashRange = lblSplashRange_1;
		
		JButton btnSellTower = new JButton("Sell Tower");
		GridBagConstraints gbc_btnSellTower = new GridBagConstraints();
		gbc_btnSellTower.fill = GridBagConstraints.VERTICAL;
		gbc_btnSellTower.insets = new Insets(0, 0, 5, 0);
		gbc_btnSellTower.gridx = 2;
		gbc_btnSellTower.gridy = 7;
		this.add(btnSellTower, gbc_btnSellTower);
		sell = btnSellTower;
		sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.getInstance().sellSelectedTower();
			}
		});
		
		
	}
	
	public void updateTowerPanel(Tower tower, int gold) {
		towerType.setText(tower.getType());
		towerDamage.setText("Damage: "+tower.getAttackDamage());
		towerSpeed.setText("Speed: "+tower.getAttackSpeed()+" /s");
		towerRange.setText("Range: "+tower.getRange());
		towerLevel.setText("Current Level: "+tower.getLevel());
		splashDamage.setText("Splash Damage: "+tower.getSplashDamage());
		splashRange.setText("Splash Range: "+tower.getSplashRange());
		
		if(!tower.fullyUpgraded()) {
			towerNextLevel.setText("Next Level: "+tower.getNextLevel());
			towerNextLevel.setVisible(true);
			towerNextDamage.setText("Damage: "+tower.getNextDamage());
			towerNextDamage.setVisible(true);
			towerNextSpeed.setText("Speed: "+tower.getNextSpeed()+" /s");
			towerNextDamage.setVisible(true);
			towerNextRange.setText("Range: "+tower.getNextRange());
			towerNextDamage.setVisible(true);
			nextSplashDamage.setText("Splash Damage: "+tower.getNextSplashDamage());
			nextSplashDamage.setVisible(true);
			nextSplashRange.setText("Splash Range: "+tower.getNextSplashRange());
			nextSplashRange.setVisible(true);
			
			upgradeCost.setText("Upgrade Cost: "+tower.getUpgradeCost());
			upgradeCost.setVisible(true);
			upgrade.setVisible(true);
			if(gold >= tower.getUpgradeCost()) {
				upgrade.setEnabled(true);
			}else {
				upgrade.setEnabled(false);
			}
			
			
		}else {
			towerNextLevel.setText("Next Level: ");
			towerNextLevel.setVisible(true);
			
			towerNextDamage.setVisible(false);
			towerNextSpeed.setVisible(false);
			towerNextRange.setVisible(false);
			nextSplashDamage.setVisible(false);
			nextSplashRange.setVisible(false);
			
			upgradeCost.setVisible(false);
			upgrade.setEnabled(false);
		}
		
		sellAmount.setText("Sell Amount: "+tower.getDestroyValue());	
		
	}
}
