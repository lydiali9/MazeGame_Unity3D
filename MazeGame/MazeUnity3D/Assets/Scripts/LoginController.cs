using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using LitJson;

public class LoginController : MonoBehaviour {

	public InputField m_account;
	public InputField m_password;
	public Text m_showMessage;
	public JsonData m_jsonValue;

	public void OnLoginButtonClick () 
	{
		string l_account = this.m_account.value;
		string l_password = this.m_password.value;
		
		if (l_account == string.Empty) {
			m_showMessage.text = "Please input accout!";
			return;
		}
		
		if (l_password == string.Empty) {
			m_showMessage.text = "Please input password!";
			return;
		}
		StartCoroutine(ScreenShot());
	}

	IEnumerator ScreenShot()
	{

		string l_account = this.m_account.value;
		string l_password = this.m_password.value;

		if (l_account != null) 
		{
			//Gets all information of player by account.
			string m_url = GetIPAddress.GetAddress() + "Login";

			WWWForm l_form = new WWWForm ();
			l_form.AddField("account", l_account);
			l_form.AddField("password", l_password);
			WWW l_getData = new WWW(m_url, l_form);

			yield return l_getData;

			if (l_getData.error != null)
			{
				m_showMessage.text = l_getData.error;
				
			}
			else
			{
				m_jsonValue = JsonMapper.ToObject(l_getData.text);
				int l_result = (int)m_jsonValue["result"];

				if(l_result == 1)
				{
					DontDestroyOnLoad(gameObject);
					Application.LoadLevel("Menu");
				}
				else if(l_result == -1)
				{
					m_showMessage.text ="account is alreay online";
				}
				else
				{
					m_showMessage.text ="account or password is not right!";
				}					
			}
		}
		else
		{
			m_showMessage.text = "Please input account";
		}
 	}

	public void ResetInput()
	{
		m_account.value = "";
		m_account.text.text = "";
		m_password.value = "";
		m_password.text.text = "";
		m_showMessage.text = "";
	}
}
