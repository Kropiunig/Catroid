/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2020 The Catrobat Team
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

package org.catrobat.catroid.test.content.bricks;

import edu.emory.mathcs.backport.java.util.Arrays;

import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.brickspinner.StringOption;
import org.catrobat.catroid.userbrick.UserBrickData;
import org.catrobat.catroid.userbrick.UserBrickInput;
import org.catrobat.catroid.userbrick.UserBrickLabel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class AddUserBrickTest {

	@Parameterized.Parameters(name = "{0}")
	public static Iterable<Object[]> data() {
		return asList(new Object[][] {
				{"SpriteBrickEmpty",
						Arrays.asList(new UserBrickData[]{defaultInput}),
						new ArrayList<>(),
						false},
				{"SameUserInputs",
						Arrays.asList(new UserBrickData[]{defaultInput}),
						Arrays.asList(new UserBrickData[]{defaultInput}),
						true},
				{"SameUserLabels",
						Arrays.asList(new UserBrickData[]{defaultLabel}),
						Arrays.asList(new UserBrickData[]{defaultLabel}),
						true},
				{"DifferentLabelsWhitespaces",
						Arrays.asList(new UserBrickData[]{new UserBrickLabel(new StringOption(" "))}),
						Arrays.asList(new UserBrickData[]{new UserBrickLabel(new StringOption(""))}),
						false},
				{"DifferentAmountOfUserInput",
						Arrays.asList(new UserBrickData[]{defaultInput, differentInput}),
						Arrays.asList(new UserBrickData[]{defaultInput}),
						false},
				{"DifferentAmountOfUserData",
						Arrays.asList(new UserBrickData[]{defaultLabel, defaultInput}),
						Arrays.asList(new UserBrickData[]{defaultInput}),
						false},
				{"SameUserDataWithDifferentInput",
						Arrays.asList(new UserBrickData[]{defaultLabel, defaultInput, defaultLabel}),
						Arrays.asList(new UserBrickData[]{defaultLabel, differentInput, defaultLabel}),
						true},
				{"DifferentUserLabelsWithDifferentInput",
						Arrays.asList(new UserBrickData[]{defaultLabel, defaultInput, differentLabel}),
						Arrays.asList(new UserBrickData[]{defaultLabel, differentInput, defaultLabel}),
						false},
		});
	}

	@Parameterized.Parameter
	public String name;

	@Parameterized.Parameter(1)
	public List<UserBrickData> brickToTestBrickData;

	@Parameterized.Parameter(2)
	public List<UserBrickData> alreadyDefinedUserBrickOfSpriteBrickData;

	@Parameterized.Parameter(3)
	public boolean expectedOutput;

	@Mock
	private Sprite spriteMock;

	private static UserBrickLabel defaultLabel = new UserBrickLabel(new StringOption("Label"));
	private static UserBrickLabel differentLabel = new UserBrickLabel(new StringOption("DifferentLabel"));
	private static UserBrickInput defaultInput = new UserBrickInput(new StringOption("Input"));
	private static UserBrickInput differentInput = new UserBrickInput(new StringOption("DifferentInput"));

	private UserDefinedBrick brickToTest;
	private List<UserDefinedBrick> userDefinedBrickListOfSprite;

	@Before
	public void setUp() throws Exception {
		brickToTest = new UserDefinedBrick(brickToTestBrickData);

		UserDefinedBrick alreadyDefinedUserBrickOfSprite = new UserDefinedBrick(alreadyDefinedUserBrickOfSpriteBrickData);
		userDefinedBrickListOfSprite = new ArrayList<>();
		userDefinedBrickListOfSprite.add(alreadyDefinedUserBrickOfSprite);
	}

	@Test
	public void testDoesUserBrickAlreadyExist() {
		spriteMock = Mockito.mock(Sprite.class);
		Mockito.when(spriteMock.getUserDefinedBrickList()).thenReturn(userDefinedBrickListOfSprite);
		assertSame(Sprite.doesUserBrickAlreadyExist(brickToTest, spriteMock), expectedOutput);
	}
}
