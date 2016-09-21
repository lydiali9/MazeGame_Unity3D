using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.Collections.Generic;
using LitJson;

public class CreateMaze : MonoBehaviour
{
	public GameObject m_wall;
	public GameObject m_player;
	public GameObject m_otherPlayer;
	public GameObject m_enemyPlayer;
	public GameObject m_spawn;
	public GameObject m_gemBig;
	public GameObject m_gemMid;
	public GameObject m_gemSmall;
	public GameObject m_medal;
	public GameObject m_trap;
	public GameObject m_room;

	public GameObject m_mazeObject;
	
	private int m_x = 10;
	private int m_y = 10;
	private int m_gemBigCount = 3;
	private int m_gemMidCount = 3;
	private int m_gemSmallCount = 3;
	private int m_medalCount = 3;
	private int m_trapCount = 2;
	private int m_roomCount = 1;
	private int m_enemyCount = 1;

	private int[,] m_map;
	private int m_loopX, m_loopY;
	private int[,] m_dir = new int[,] {{0, 1}, {1, 0},{0, -1}, {-1, 0}};
	private int[] m_spawnPosition = new int[2];
	private int[] m_otherPlayerId;

	private float m_latitude;
	private float m_longitude;
	private int m_playerId;
	private int m_currentMapId;
	private int m_myMapId;
	private int m_targetPlayerId;

	// Use this for initialization
	void Start ()
	{
		RankButtonController l_script = GameObject.Find("MenuObject").GetComponent("RankButtonController") as RankButtonController;
		m_playerId = l_script.m_id;
		Destroy(GameObject.Find("MenuObject"));
		StartCoroutine(GetGPS());
	}

	IEnumerator GetGPS()
	{
//		if (Input.location.isEnabledByUser)
//		{
//			return false;
//		}

		Input.location.Start(10, 10);

		int l_maxWait = 20;
		while(Input.location.status == LocationServiceStatus.Initializing && l_maxWait >0)
		{
			yield return new WaitForSeconds(1);
			l_maxWait--;
		}

		if (l_maxWait < 1)
		{
			yield return false;
		}

		if (Input.location.status == LocationServiceStatus.Failed)
		{
			yield return false;
		}
		else
		{
			m_latitude = Input.location.lastData.latitude;
			m_longitude = Input.location.lastData.longitude;
			StartCoroutine(InitMap());
		}
		Input.location.Stop();
	}

	IEnumerator InitMap() 
	{
		WWWForm l_form = new WWWForm();

		l_form.AddField("longitude", m_longitude.ToString());
		l_form.AddField("latitude", m_latitude.ToString());
		l_form.AddField("id", m_playerId);

		string l_urlPosition = GetIPAddress.GetAddress() + "CheckPosition";
		WWW l_checkPosition = new WWW(l_urlPosition, l_form);

		yield return l_checkPosition;

		if (l_checkPosition.error != null)
		{
			//TODO handle error.
			Debug.Log(l_checkPosition.error);
		}
		else
		{
			JsonData l_json = JsonMapper.ToObject(l_checkPosition.text);
			if ((int)l_json["result"] == 0)
			{
				StartCoroutine(MakeMaze());
			}
			if ((int)l_json["result"] == 1)
			{
				if ((int)l_json["mapId"] !=0)
				{
					StartCoroutine(GetMap((int)l_json["mapId"]));
				}
				else
				{
					StartCoroutine(MakeMaze());
				}
			}
		}
	}
	
	void PrintMaze()  
	{
		GameObject l_newObject;
		for (m_loopY = 1; m_loopY < m_map.GetLength(1); m_loopY++)
		{
			for (m_loopX = 1; m_loopX <  m_map.GetLength(0); m_loopX++)
			{
				switch (m_map[m_loopX, m_loopY])
				{
				case -1:
					l_newObject = (GameObject)Instantiate(m_spawn, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					m_spawnPosition[0] = m_loopX;
					m_spawnPosition[1] = m_loopY;
					break;
				case 0:
					break;
				case 1:
					l_newObject = (GameObject)Instantiate(m_wall, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				case 2:
					l_newObject = (GameObject)Instantiate(m_gemBig, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				case 3:
					l_newObject = (GameObject)Instantiate(m_medal, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				case 4:
					l_newObject = (GameObject)Instantiate(m_trap, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				case 5:
					l_newObject = (GameObject)Instantiate(m_room, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				case 6:
					l_newObject = (GameObject)Instantiate(m_gemMid, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				case 7:
					l_newObject = (GameObject)Instantiate(m_gemSmall, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					break;
				default:
					//create other player.
					l_newObject = (GameObject)Instantiate(m_enemyPlayer, new Vector3 (m_loopX, 0, m_loopY), new Quaternion (0, 0, 0, 0));
					l_newObject.transform.parent = m_mazeObject.transform;
					l_newObject.SendMessage("setPlayerId", m_map[m_loopX, m_loopY], SendMessageOptions.DontRequireReceiver);
					break;
				}
			}
		}
	}
	
	IEnumerator MakeMaze()  
	{
		WWWForm l_form = new WWWForm();
		
		l_form.AddField("longitude", m_longitude.ToString());
		l_form.AddField("latitude", m_latitude.ToString());
		l_form.AddField("playerId", m_playerId.ToString());
		
		string l_urlResources = GetIPAddress.GetAddress() + "GetMapResource";
		WWW l_getResources = new WWW(l_urlResources, l_form);

		JsonData l_jsonResources = new JsonData();
		
		yield return l_getResources;
		
		if (l_getResources.error != null)
		{
			//TODO handle error.
			Debug.Log(l_getResources.error);
		}
		else
		{
			l_jsonResources = JsonMapper.ToObject(l_getResources.text);
		}

		m_x = (int)l_jsonResources["sizeX"];
		m_y = (int)l_jsonResources["sizeY"];
		m_gemBigCount = (int)l_jsonResources["bdiamonds"];
		m_gemMidCount = (int)l_jsonResources["mdiamonds"];
		m_gemSmallCount = (int)l_jsonResources["sdiamonds"];
		m_medalCount = (int)l_jsonResources["medal"];
		m_trapCount = (int)l_jsonResources["trapNumb"];
		m_enemyCount = (int)l_jsonResources["playerNumb"];
		//TODO room.

		if (m_enemyCount > 0)
		{
			m_otherPlayerId = new int[m_enemyCount];
			for (int i = 0; i < l_jsonResources["player"].Count; i++)
			{
				m_otherPlayerId[i] = (int)l_jsonResources["player"][i]["playerId"];
			}
		}

		m_map = new int[m_x * 2 + 3, m_y * 2 + 3];
		for (int i = 0; i < m_x * 2 + 3; ++i)
		{
			for (int j = 0; j < m_y * 2 + 3; ++j)
			{
				m_map [i, j] = 1;  
			}
		}

		for(m_loopX = 0, m_loopY = 2 * m_y + 2; m_loopX <= 2 * m_x + 2; ++m_loopX)  
		{  
			m_map[m_loopX, 0] = 0;  
			m_map[m_loopX, m_loopY] = 0;  
		}  
		for(m_loopX = 0, m_loopY = 2 * m_x + 2; m_loopX <= 2 * m_y + 2; ++m_loopX)  
		{  
			m_map[0, m_loopX] = 0;  
			m_map[m_loopY, m_loopX] = 0;  
		}

		SearchPath(Random.Range(0, m_x) + 1, Random.Range(0, m_y) + 1);  

		EditMaze();
		PrintMaze();
		GameObject l_player = (GameObject)GameObject.Instantiate(m_player, new Vector3 (2, 0, 2), new Quaternion (0, 0, 0, 0));
		l_player.transform.parent = m_mazeObject.transform;
		
		if (GameObject.FindGameObjectWithTag("Respawn"))
		{
			GameObject l_spawn = GameObject.FindGameObjectWithTag ("Respawn");
			l_player.transform.position = l_spawn.transform.position;
		}
		else
		{
			GameObject l_newSpawn;
			l_newSpawn = (GameObject)Instantiate (m_spawn, new Vector3 (2, 0, 2), new Quaternion (0, 0, 0, 0));
			l_newSpawn.transform.parent = m_mazeObject.transform;
			m_spawnPosition[0] = 2;
			m_spawnPosition[1] = 2;
		}
		GameObject.FindGameObjectWithTag("UICamera").SendMessage("InitUI", SendMessageOptions.DontRequireReceiver);
		StartCoroutine(Upload());
	}  
	
	void SearchPath(int p_x, int p_y)  
	{  
		int l_x = p_x * 2;  
		int l_y = p_y * 2;
		int l_turn;

		m_map[l_x, l_y] = 0;
		l_turn = (Random.Range(0, 2) > 0) ? 1 : 3;

		for(int i = 0, l_next = Random.Range(0, 4); i < 4; ++i, l_next = (l_next + l_turn) % 4)
		{
			if(m_map[l_x + 2 * m_dir[l_next, 0],l_y + 2 * m_dir[l_next, 1]] == 1)  
			{  
				m_map[l_x + m_dir[l_next, 0], l_y + m_dir[l_next, 1]] = 0;  
				SearchPath(p_x + m_dir[l_next, 0], p_y + m_dir[l_next, 1]);  
			}
		}
	}

	void EditMaze()
	{
		int l_destroyCount = m_x * 2 + m_y * 2;
		int l_x;
		int l_y;
		while (l_destroyCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 1)
			{
				m_map[l_x,l_y] = 0;
				l_destroyCount--;
			}
		}
		while (m_gemBigCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = 2;
				m_gemBigCount--;
			}
		}
		while (m_medalCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = 3;
				m_medalCount--;
			}
		}
		while (m_trapCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = 4;
				m_trapCount--;
			}
		}
		while (m_roomCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = 5;
				m_roomCount--;
			}
		}
		while (m_gemMidCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = 6;
				m_gemMidCount--;
			}
		}
		while (m_gemSmallCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = 7;
				m_gemSmallCount--;
			}
		}
		while (m_enemyCount > 0)
		{
			l_x = Random.Range(3, m_x * 2 - 1);
			l_y = Random.Range(3, m_y * 2 - 1);
			if (m_map[l_x,l_y] == 0)
			{
				m_map[l_x,l_y] = m_otherPlayerId[m_enemyCount - 1];
				m_enemyCount--;
			}
		}
	}

	IEnumerator Upload()
	{
		WWWForm l_form = new WWWForm();

		l_form.AddField("playerId", m_playerId.ToString());
		l_form.AddField ("map", MapToJSON(m_map));
		
		string l_urlUpload = GetIPAddress.GetAddress() + "UploadMap";
		WWW l_upload = new WWW(l_urlUpload, l_form);
		
		JsonData l_uploadReturn = new JsonData();
		
		yield return l_upload;
		
		if (l_upload.error != null)
		{
			//TODO handle error.
			Debug.Log(l_upload.error);
		}
		else
		{
			l_uploadReturn = JsonMapper.ToObject(l_upload.text);
		}
		m_currentMapId = (int)l_uploadReturn["mapId"];
		m_myMapId = m_currentMapId;
	}

	void GetObject(Vector3 p_position)
	{
		m_map[m_spawnPosition[0],m_spawnPosition[1]] = 0;
		int l_resourceType = m_map [Mathf.RoundToInt(p_position.x), Mathf.RoundToInt(p_position.z)];
		m_map[Mathf.RoundToInt(p_position.x), Mathf.RoundToInt(p_position.z)] = -1;
		m_spawnPosition[0] = Mathf.RoundToInt(p_position.x);
		m_spawnPosition[1] = Mathf.RoundToInt(p_position.z);
		UpdateResource(l_resourceType);
	}

	void StealObject(Vector3 p_position)
	{
		int l_resourceType = m_map [Mathf.RoundToInt(p_position.x), Mathf.RoundToInt(p_position.z)];
		m_map[Mathf.RoundToInt(p_position.x), Mathf.RoundToInt(p_position.z)] = 0;
		UpdateResource(l_resourceType);
	}

	void UpdateResource(int p_resourceType)
	{
		WWWForm l_form = new WWWForm();
		
		l_form.AddField("mapId", m_currentMapId);
		l_form.AddField("playerId", m_playerId);
		l_form.AddField ("map", MapToJSON(m_map));
		l_form.AddField ("resourceType", p_resourceType);
		
		string l_urlUpdate = GetIPAddress.GetAddress() + "UpdateResource";
		new WWW(l_urlUpdate, l_form);
	}

	string MapToJSON(int[,] p_map)
	{
		string l_mapJSON = "{\"map\":[";
		for (int i = 0; i < p_map.GetLength(0); ++i)
		{
			if (i > 0)
			{
				l_mapJSON += ",";
			}
			l_mapJSON += "{\"row\":[";
			for (int j = 0; j < p_map.GetLength(1); ++j)
			{
				if (j > 0)
				{
					l_mapJSON += ",";
				}
				l_mapJSON += "{\"column\":" + m_map [i, j].ToString() + "}";
			}
			l_mapJSON += "]}";
		}
		l_mapJSON += "]}";
		return l_mapJSON;
	}

	IEnumerator GetMap(int p_mapId)
	{
		m_myMapId = p_mapId;
		m_currentMapId = p_mapId;
		WWWForm l_formTemp = new WWWForm();
		l_formTemp.AddField("mapId", p_mapId);
		string l_urlMap = GetIPAddress.GetAddress() + "GetMap";
		WWW l_getMap = new WWW(l_urlMap, l_formTemp);

		yield return l_getMap;
		
		JsonData l_jsonMap = new JsonData();
		if (l_getMap.error != null)
		{
			Debug.Log(l_getMap.error);
		}
		else
		{
			l_jsonMap = JsonMapper.ToObject(l_getMap.text);
		}
		
		JsonData l_map =  l_jsonMap["map"];
		m_map = new int[l_map.Count, l_map[0]["row"].Count];
		for (int i = 0; i < l_map.Count; ++i)
		{
			JsonData l_row = l_map[i]["row"];
			for (int j = 0; j < l_row.Count; ++j)  
			{
				m_map [i, j] = (int)l_row[j]["column"];
			}
		}
		PrintMaze();
		GameObject l_player = (GameObject)GameObject.Instantiate(m_player, new Vector3 (2, 0, 2), new Quaternion (0, 0, 0, 0));
		l_player.transform.parent = m_mazeObject.transform;
		
		if (GameObject.FindGameObjectWithTag("Respawn"))
		{
			GameObject l_spawn = GameObject.FindGameObjectWithTag ("Respawn");
			l_player.transform.position = l_spawn.transform.position;
		}
		else
		{
			GameObject l_newSpawn;
			l_newSpawn = (GameObject)Instantiate (m_spawn, new Vector3 (2, 0, 2), new Quaternion (0, 0, 0, 0));
			l_newSpawn.transform.parent = m_mazeObject.transform;
			m_spawnPosition[0] = 2;
			m_spawnPosition[1] = 2;
		}
		GameObject.FindGameObjectWithTag("UICamera").SendMessage("InitUI", SendMessageOptions.DontRequireReceiver);
	}

	IEnumerator GetOtherMap(int p_mapId)
	{
		m_currentMapId = p_mapId;
		WWWForm l_formTemp = new WWWForm();
		l_formTemp.AddField("mapId", p_mapId);
		string l_urlotherMap = GetIPAddress.GetAddress() + "GetOtherMap";
		WWW l_getotherMap = new WWW(l_urlotherMap, l_formTemp);

		yield return l_getotherMap;
		
		JsonData l_jsonMap = new JsonData();
		if (l_getotherMap.error != null)
		{
			Debug.Log(l_getotherMap.error);
		}
		else
		{
			l_jsonMap = JsonMapper.ToObject(l_getotherMap.text);
		}
		
		JsonData l_map =  l_jsonMap["map"];
		m_map = new int[l_map.Count, l_map[0]["row"].Count];
		for (int i = 0; i < l_map.Count; ++i)
		{
			JsonData l_row = l_map[i]["row"];
			for (int j = 0; j < l_row.Count; ++j)  
			{
				m_map [i, j] = (int)l_row[j]["column"];
			}
		}
		PrintMaze ();
		GameObject l_otherPlayer = (GameObject)GameObject.Instantiate(m_otherPlayer, new Vector3 (2, 0, 2), new Quaternion (0, 0, 0, 0));
		GameObject.FindGameObjectWithTag("UICamera").SendMessage("InitUI", SendMessageOptions.DontRequireReceiver);
		l_otherPlayer.transform.parent = m_mazeObject.transform;
	}
	
	IEnumerator TryEnterOtherPlayer(int p_playerId)
	{
		m_targetPlayerId = p_playerId;
		string l_urlGetState = GetIPAddress.GetAddress() + "GetOtherState";
		WWWForm l_formTemp = new WWWForm();
		l_formTemp.AddField("targetPlayerId", p_playerId);
		l_formTemp.AddField("playerId", m_playerId);
		l_formTemp.AddField("longitude", m_longitude.ToString());
		l_formTemp.AddField("latitude", m_latitude.ToString());

		WWW l_getState = new WWW(l_urlGetState, l_formTemp);

		yield return l_getState;

		JsonData l_jsonResult = new JsonData();
		if (l_getState.error != null)
		{
			Debug.Log(l_getState.error);
		}
		else
		{
			l_jsonResult = JsonMapper.ToObject(l_getState.text);
		}

		GameObject l_mainPlayer = GameObject.FindGameObjectWithTag("MainPlayerObject");

		switch ((int)l_jsonResult["result"])
		{
		case 2:
			foreach(Transform child in m_mazeObject.transform)
			{
				Destroy(child.gameObject);
			}
			StartCoroutine(GetOtherMap((int)l_jsonResult["mapId"]));
			break;
		case 1:
			l_mainPlayer.SendMessage("FinishEnter", SendMessageOptions.DontRequireReceiver);
			l_mainPlayer.SendMessage("OnlineState", SendMessageOptions.DontRequireReceiver);
			break;
		case 0:
			l_mainPlayer.SendMessage("FinishEnter", SendMessageOptions.DontRequireReceiver);
			l_mainPlayer.SendMessage("WrongArea", SendMessageOptions.DontRequireReceiver);
			break;
		default:
			l_mainPlayer.SendMessage("FinishEnter", SendMessageOptions.DontRequireReceiver);
			l_mainPlayer.SendMessage("FailToEnter", SendMessageOptions.DontRequireReceiver);
			break;
		}
	}

	void ExitOtherPlayer()
	{
		foreach(Transform child in m_mazeObject.transform)
		{
			Destroy(child.gameObject);
		}

		string l_urlGetState = GetIPAddress.GetAddress() + "QuitOtherMap  ";
		WWWForm l_formTemp = new WWWForm();
		l_formTemp.AddField("targetPlayerId", m_targetPlayerId);
		new WWW(l_urlGetState, l_formTemp);

		StartCoroutine(GetMap(m_myMapId));
	}
}