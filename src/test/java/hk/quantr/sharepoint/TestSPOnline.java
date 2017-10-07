// License : Apache License Version 2.0  https://www.apache.org/licenses/LICENSE-2.0
package hk.quantr.sharepoint;

import com.peterswing.CommonLib;
import hk.quantr.sharepoint.SPOnline;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

/**
 *
 * @author Peter <mcheung63@hotmail.com>
 */
public class TestSPOnline {

	@Test
	public void test1() {
		JPasswordField pwd = new JPasswordField(10);
		int action = JOptionPane.showConfirmDialog(null, pwd, "Please input office365 password", JOptionPane.OK_CANCEL_OPTION);
		String password = new String(pwd.getPassword());
		String domain = "quantr";
		Pair<String, String> token = SPOnline.login("peter@quantr.hk", password, domain);
		if (token != null) {
			String json = SPOnline.web(token, domain);
			System.out.println(CommonLib.prettyFormatJson(json));
		}
	}
}
