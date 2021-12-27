package com.example.game.ui.plane_upgrade;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.example.game.game.resource.ImageResource;
import com.example.game.game.resource.ImageResourceType;
import com.example.game.ui.UIButton;

public class UIUpgradeButton extends UIButton {
    public UIUpgradeButton(ImageResource imageResource, ImageResourceType upgradeButton, PointF pointF) {
        super(imageResource, upgradeButton,  pointF);
    }
}