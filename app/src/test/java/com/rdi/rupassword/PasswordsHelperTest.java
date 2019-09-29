package com.rdi.rupassword;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class PasswordsHelperTest {

    public static final String[] RUS = {"й", "ц", "у", "к", "е", "н"};
    public static final String[] ENG = {"q", "w", "e", "r", "t", "y"};

    public static final String[] SOURSES =
                        {"", "некуцй",  "фывапй"};

    public static final String[] RESULTS =
            {"", "ytrewq",  "фывапq"};

    private PasswordsHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new PasswordsHelper(RUS, ENG);
    }

    @Test
    public void convert() {
        assertTrue("Error in test case" , SOURSES.length==RESULTS.length);

        for (int i = 0; i <SOURSES.length ; i++) {
            assertEquals(RESULTS[i], helper.convert(SOURSES[i]));
        }


    }

    @Test (expected = IllegalArgumentException.class)
    public void convertIsThrows() {
        new PasswordsHelper((RUS), new String[]{});

    }


}