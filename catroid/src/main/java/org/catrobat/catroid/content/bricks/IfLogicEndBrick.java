/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IfLogicEndBrick extends BrickBaseType implements NestingBrick, AllowedAfterDeadEndBrick {

	private static final long serialVersionUID = 1L;
	private static final String TAG = IfLogicEndBrick.class.getSimpleName();

	private transient IfLogicElseBrick ifElseBrick;
	private transient IfLogicBeginBrick ifBeginBrick;

	public IfLogicEndBrick(IfLogicElseBrick elseBrick, IfLogicBeginBrick beginBrick) {
		this.ifElseBrick = elseBrick;
		this.ifBeginBrick = beginBrick;
	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	public IfLogicElseBrick getIfElseBrick() {
		return ifElseBrick;
	}

	public IfLogicBeginBrick getIfBeginBrick() {
		return ifBeginBrick;
	}

	public void setIfElseBrick(IfLogicElseBrick ifElseBrick) {
		this.ifElseBrick = ifElseBrick;
	}

	public void setIfBeginBrick(IfLogicBeginBrick ifBeginBrick) {
		this.ifBeginBrick = ifBeginBrick;
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}

		view = View.inflate(context, R.layout.brick_if_end_if, null);
		view = BrickViewProvider.setAlphaOnView(view, alphaValue);

		setCheckboxView(R.id.brick_if_end_if_checkbox);
		return view;
	}

	@Override
	public Brick clone() {
		return new IfLogicEndBrick(ifElseBrick, ifBeginBrick);
	}

	@Override
	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.brick_if_end_if, null);
	}

	@Override
	public boolean isDraggableOver(Brick brick) {
		return brick != ifElseBrick;
	}

	@Override
	public boolean isInitialized() {
		return ifElseBrick != null;
	}

	@Override
	public void initialize() {
		Log.w(TAG, "Cannot create the IfLogic Bricks from here!");
	}

	@Override
	public List<NestingBrick> getAllNestingBrickParts(boolean sorted) {
		//TODO: handle sorting
		List<NestingBrick> nestingBrickList = new ArrayList<NestingBrick>();
		if (sorted) {
			nestingBrickList.add(ifBeginBrick);
			nestingBrickList.add(ifElseBrick);
			nestingBrickList.add(this);
		} else {
			nestingBrickList.add(this);
			nestingBrickList.add(ifBeginBrick);
			//nestingBrickList.add(ifElseBrick);
		}

		return nestingBrickList;
	}

	@Override
	public List<SequenceAction> addActionToSequence(Sprite sprite, SequenceAction sequence) {
		LinkedList<SequenceAction> returnActionList = new LinkedList<SequenceAction>();
		returnActionList.add(sequence);
		return returnActionList;
	}
}
