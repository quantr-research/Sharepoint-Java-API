/*
 * Copyright (C) 2017 Peter <mcheung63@hotmail.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.github.mcheung63.test;

import hk.quantr.sharepoint.SPOnline;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

/**
 *
 * @author Peter <mcheung63@hotmail.com>
 */
public class TestSPOnline {

	@Test
	public void test1() {
		Pair<String, String> pair = SPOnline.login("username", "password", "quantr");
		System.out.println(pair.getLeft());
		System.out.println(pair.getRight());
	}
}
