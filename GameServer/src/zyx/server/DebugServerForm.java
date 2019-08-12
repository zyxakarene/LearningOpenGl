package zyx.server;

import java.awt.Graphics;
import java.net.InetAddress;
import zyx.game.vo.Gender;
import zyx.net.io.connections.ConnectionData;
import zyx.server.world.RoomItems;
import zyx.server.world.humanoids.handheld.guests.BillItem;
import zyx.server.world.humanoids.npc.NpcManager;
import zyx.server.world.humanoids.players.Player;
import zyx.server.world.humanoids.players.PlayerManager;
import zyx.server.world.interactable.common.DinnerTable;
import zyx.server.world.interactable.common.FoodTable;
import zyx.server.world.interactable.common.player.PlayerInteraction;
import zyx.server.world.interactable.guests.GuestChair;
import zyx.server.world.interactable.player.OrderMachine;

public class DebugServerForm extends javax.swing.JFrame
{

	public static RoomItems room;

	private Player player;

	public DebugServerForm()
	{
		initComponents();

		ConnectionData dummyData = new ConnectionData(InetAddress.getLoopbackAddress(), 9999);
		player = PlayerManager.getInstance().createPlayer("Test", Gender.MALE, dummyData);
		player.updatePosition(100, 200, 0);

		Thread repainter = new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					repaint();

					try
					{
						Thread.sleep(50);
					}
					catch (InterruptedException ex)
					{
					}
				}
			}
		};
		repainter.setDaemon(true);
		repainter.start();
	}

	private void onDrawPanel(Graphics g)
	{
		room.draw(g);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        getOrderBtn = new javax.swing.JButton();
        addOrderBtn = new javax.swing.JButton();
        takeFoodBtn = new javax.swing.JButton();
        deliverFoodBtn = new javax.swing.JButton();
        deliverBiillBtn = new javax.swing.JButton();
        addGuestBtn = new javax.swing.JButton();
        drawPanel = new javax.swing.JPanel()
        {
            public void paint(Graphics g)
            {
                super.paint(g);
                onDrawPanel(g);
            }
        }
        ;

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getOrderBtn.setText("Get Order");
        getOrderBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                getOrderBtnActionPerformed(evt);
            }
        });

        addOrderBtn.setText("Punch in Order");
        addOrderBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addOrderBtnActionPerformed(evt);
            }
        });

        takeFoodBtn.setText("Pickup food");
        takeFoodBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                takeFoodBtnActionPerformed(evt);
            }
        });

        deliverFoodBtn.setText("Deliver food");
        deliverFoodBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                deliverFoodBtnActionPerformed(evt);
            }
        });

        deliverBiillBtn.setText("Deliver Bill");
        deliverBiillBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                deliverBiillBtnActionPerformed(evt);
            }
        });

        addGuestBtn.setText("Add Guest");
        addGuestBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addGuestBtnActionPerformed(evt);
            }
        });

        drawPanel.setBackground(new java.awt.Color(255, 255, 255));
        drawPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout drawPanelLayout = new javax.swing.GroupLayout(drawPanel);
        drawPanel.setLayout(drawPanelLayout);
        drawPanelLayout.setHorizontalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 265, Short.MAX_VALUE)
        );
        drawPanelLayout.setVerticalGroup(
            drawPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(getOrderBtn)
                    .addComponent(addOrderBtn)
                    .addComponent(addGuestBtn)
                    .addComponent(takeFoodBtn)
                    .addComponent(deliverFoodBtn)
                    .addComponent(deliverBiillBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(drawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addGuestBtn)
                        .addGap(37, 37, 37)
                        .addComponent(getOrderBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addOrderBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(takeFoodBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deliverFoodBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deliverBiillBtn)
                        .addGap(0, 79, Short.MAX_VALUE))
                    .addComponent(drawPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void getOrderBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_getOrderBtnActionPerformed
    {//GEN-HEADEREND:event_getOrderBtnActionPerformed
		GuestChair chair = room.chairs[0];
		chair.interactWith(player, PlayerInteraction.take());
    }//GEN-LAST:event_getOrderBtnActionPerformed

    private void addOrderBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addOrderBtnActionPerformed
    {//GEN-HEADEREND:event_addOrderBtnActionPerformed
		OrderMachine machine = room.orderMachine;
		machine.interactWith(player);
    }//GEN-LAST:event_addOrderBtnActionPerformed

    private void takeFoodBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_takeFoodBtnActionPerformed
    {//GEN-HEADEREND:event_takeFoodBtnActionPerformed
		FoodTable table = room.foodTables[0];
		int firstId = table.debug_GetFirstItemOnTable();
		table.interactWith(player, PlayerInteraction.take(firstId));
    }//GEN-LAST:event_takeFoodBtnActionPerformed

    private void deliverFoodBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deliverFoodBtnActionPerformed
    {//GEN-HEADEREND:event_deliverFoodBtnActionPerformed
		DinnerTable table = room.dinnerTables[0];
		table.interactWith(player, PlayerInteraction.give());
    }//GEN-LAST:event_deliverFoodBtnActionPerformed

    private void deliverBiillBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deliverBiillBtnActionPerformed
    {//GEN-HEADEREND:event_deliverBiillBtnActionPerformed
		DinnerTable table = room.dinnerTables[0];
		
		if (table.canReceiveBill())
		{
			player.pickupItem(new BillItem());
			table.interactWith(player, PlayerInteraction.give());
		}
    }//GEN-LAST:event_deliverBiillBtnActionPerformed

    private void addGuestBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addGuestBtnActionPerformed
    {//GEN-HEADEREND:event_addGuestBtnActionPerformed
		NpcManager.getInstance().addGuestGroup();
    }//GEN-LAST:event_addGuestBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGuestBtn;
    private javax.swing.JButton addOrderBtn;
    private javax.swing.JButton deliverBiillBtn;
    private javax.swing.JButton deliverFoodBtn;
    private javax.swing.JPanel drawPanel;
    private javax.swing.JButton getOrderBtn;
    private javax.swing.JButton takeFoodBtn;
    // End of variables declaration//GEN-END:variables
}
