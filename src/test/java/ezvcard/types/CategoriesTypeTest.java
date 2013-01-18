package ezvcard.types;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ezvcard.VCardSubTypes;
import ezvcard.VCardVersion;
import ezvcard.io.CompatibilityMode;

/*
 Copyright (c) 2012, Michael Angstadt
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met: 

 1. Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer. 
 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution. 

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 The views and conclusions contained in the software and documentation are those
 of the authors and should not be interpreted as representing official policies, 
 either expressed or implied, of the FreeBSD Project.
 */

/**
 * @author Michael Angstadt
 */
public class CategoriesTypeTest {
	@Test
	public void doMarshalValue() throws Exception {
		VCardVersion version = VCardVersion.V2_1;
		List<String> warnings = new ArrayList<String>();
		CompatibilityMode compatibilityMode;
		CategoriesType t;
		String expected, actual;

		//comma delimiters are escaped for KDE
		compatibilityMode = CompatibilityMode.KDE_ADDRESS_BOOK;
		t = new CategoriesType();
		t.addValue("One");
		t.addValue("T,wo");
		t.addValue("Thr;ee");
		expected = "One\\,T\\,wo\\,Thr\\;ee";
		actual = t.marshalText(version, warnings, compatibilityMode);
		assertEquals(expected, actual);

		compatibilityMode = CompatibilityMode.RFC;
		t = new CategoriesType();
		t.addValue("One");
		t.addValue("T,wo");
		t.addValue("Thr;ee");
		expected = "One,T\\,wo,Thr\\;ee";
		actual = t.marshalText(version, warnings, compatibilityMode);
		assertEquals(expected, actual);
	}

	@Test
	public void doUnmarshalValue() throws Exception {
		VCardVersion version = VCardVersion.V2_1;
		List<String> warnings = new ArrayList<String>();
		CompatibilityMode compatibilityMode;
		VCardSubTypes subTypes = new VCardSubTypes();
		CategoriesType t;
		List<String> expected, actual;

		//comma delimiters are escaped for KDE
		compatibilityMode = CompatibilityMode.KDE_ADDRESS_BOOK;
		t = new CategoriesType();
		t.unmarshalText(subTypes, "One\\,T\\,wo\\,Thr\\;ee", version, warnings, compatibilityMode);
		expected = Arrays.asList("One", "T", "wo", "Thr;ee");
		actual = t.getValues();
		assertEquals(expected, actual);

		compatibilityMode = CompatibilityMode.RFC;
		t = new CategoriesType();
		t.unmarshalText(subTypes, "One\\,T\\,wo\\,Thr\\;ee", version, warnings, compatibilityMode);
		expected = Arrays.asList("One,T,wo,Thr;ee");
		actual = t.getValues();
		assertEquals(expected, actual);
	}
}
