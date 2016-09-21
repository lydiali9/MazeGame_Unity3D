using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using LitJson;

public class RankButtonController : MonoBehaviour
{

	public GameObject m_pankObject;
	public int m_id;
	public JsonData m_jsonResult;

	public UnityEngine.UI.Text m_medalText;
	public UnityEngine.UI.Text m_bdiamondsText;
	public UnityEngine.UI.Text m_mdiamondsText;
	public UnityEngine.UI.Text m_sdiamondsText;
	public UnityEngine.UI.Text m_messageText;
	public UnityEngine.UI.Text m_breakTimeText;

	private bool m_located;
	private float m_latitude;
	private float m_longitude;
	private float m_altitude;
	private float m_horizontalAccuracy;

	private bool m_isOut = true;

	void Start()
	{
		m_messageText.text = "";
		m_breakTimeText.text = "";
		LoginController l_cs = GameObject.Find ("LoginMainCamera").GetComponent ("LoginController") as LoginController;
		m_id = (int)l_cs.m_jsonValue["id"];
		GameObject.Find ("LoginMainCamera").camera.enabled = false;
		StartCoroutine(GetPlayerResource());
	}

	void Update()
	{
		if (Input.GetKeyUp(KeyCode.Escape))
		{
			Application.Quit();
		}
	}
	
	public void OnRankButtonClick()
	{
		if (m_isOut) 
		{
			iTween.MoveTo(m_pankObject, m_pankObject.transform.position + new Vector3(120, 0, 0), 0);
			m_isOut = false;
		} 
		else 
		{
			iTween.MoveTo(m_pankObject, m_pankObject.transform.position - new Vector3(120, 0, 0), 0);
			m_isOut = true;
		}

	}

	IEnumerator GetPlayerResource()
	{
		WWWForm l_form = new WWWForm();
		
		l_form.AddField("playerId", m_id);
		
		string l_urlPlayerResource = GetIPAddress.GetAddress() + "GetPlayerResource";
		WWW l_playerResource = new WWW(l_urlPlayerResource, l_form);
		
		JsonData l_resourceData = new JsonData();
		
		yield return l_playerResource;
		
		if (l_playerResource.error != null)
		{
			//TODO handle error.
			Debug.Log(l_playerResource.error);
		}
		else
		{
			l_resourceData = JsonMapper.ToObject(l_playerResource.text);
		}

		m_medalText.text = l_resourceData["medal"].ToString();
		m_bdiamondsText.text = l_resourceData["bdiamonds"].ToString();
		m_mdiamondsText.text = l_resourceData["mdiamonds"].ToString();
		m_sdiamondsText.text = l_resourceData["sdiamonds"].ToString();
	}

	public void OnStartButtonClick()
	{
		StartCoroutine(ScreenShot());
	}

	IEnumerator ScreenShot()
	{
		DontDestroyOnLoad(gameObject);

		string m_urlCheckState = GetIPAddress.GetAddress() + "CheckState";
		
		WWWForm l_form = new WWWForm ();
		l_form.AddField ("id", m_id);
		WWW l_getData = new WWW (m_urlCheckState, l_form);

		yield return l_getData;

		m_jsonResult = JsonMapper.ToObject (l_getData.text);
		int l_isBreak = (int)m_jsonResult ["isBreak"];

		if (l_isBreak != 0)
		{
			m_messageText.text = "玩家闯入中,预计剩余时间:";
			do
			{
				string m_urlCheckStateGetBreakTime = GetIPAddress.GetAddress() + "GetBreakTime";
				l_getData = new WWW (m_urlCheckStateGetBreakTime, l_form);

				yield return l_getData;
//				while (!l_getData.isDone)
//				{
//					yield return new WaitForSeconds (1);
//				}
				m_jsonResult = JsonMapper.ToObject(l_getData.text);

				int l_second = (int)m_jsonResult ["time"];
				if (l_second > 0)
				{
					m_breakTimeText.text = l_second + " 秒";
				}
				else
				{
					break;
				}

				l_getData = new WWW (m_urlCheckState, l_form);

				yield return l_getData;
//				while (!l_getData.isDone)
//				{
//					yield return new WaitForSeconds (1);
//				}
				m_jsonResult = JsonMapper.ToObject (l_getData.text);
				l_isBreak = (int)m_jsonResult ["isBreak"];

				yield return new WaitForSeconds(1);
			}
			while (l_isBreak != 0);
		}
		Application.LoadLevel("Maze");
	}

	void OnApplicationQuit()
	{
		string m_url = GetIPAddress.GetAddress() + "Logout";
		
		WWWForm form = new WWWForm ();
		form.AddField ("playerId", m_id);
		new WWW(m_url, form);
	}

	public void Exit()
	{
		Application.Quit();
	}
}
