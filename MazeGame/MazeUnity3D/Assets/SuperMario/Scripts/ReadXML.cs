using UnityEngine;
using System.Collections;
using System.Xml;

public class ReadXML : MonoBehaviour {

	public GameObject m_ground;
	public GameObject m_brick;
	public GameObject m_mushroomBox;
	public GameObject m_lifeMushroomBox;
	public GameObject m_coinBox;
	public GameObject m_starBox;
	public GameObject m_enemyNormal;
	public GameObject m_enemyTortoise;
	public GameObject m_coin;
	public GameObject m_pipeline;
	public GameObject m_coinBrick;
	public GameObject m_stone;

	// Use this for initialization
	void Start ()
	{
		InitLevel("Level1.xml");
	}
	
	// Update is called once per frame
	void Update ()
	{
	
	}

	public void InitLevel(string p_fileName)
	{
		string l_data = Resources.Load(p_fileName.Split('.')[0]).ToString();
		XmlDocument l_xmlDoc = new XmlDocument();
		l_xmlDoc.LoadXml(l_data);

		XmlNodeList l_nodeList = l_xmlDoc.SelectNodes("GameObject/Object");

		GameObject l_newObject;

		foreach (XmlNode l_node in l_nodeList)
		{
			XmlNodeList l_intiParams = l_node.ChildNodes;
			string l_name = "";
			Vector3 l_position = new Vector3(0, 0, 0);
			l_name = l_intiParams[0].InnerText;
			l_position.x = int.Parse(l_intiParams[1].InnerText);
			l_position.y = int.Parse(l_intiParams[2].InnerText);
			l_position.z = int.Parse(l_intiParams[3].InnerText);

			if (l_name.Equals("Ground"))
			{
				int l_length = int.Parse(l_intiParams[4].InnerText);
				for (int i = 0; i< l_length; i++)
				{
					l_newObject = (GameObject)Instantiate(m_ground, l_position + new Vector3(0, 0, i), new Quaternion(0, 0, 0, 0));
					l_newObject.transform.parent = gameObject.transform;
				}
			}

			if (l_name.Equals("Brick"))
			{
				l_newObject = (GameObject)Instantiate(m_brick, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("MushroomBox"))
			{
				l_newObject = (GameObject)Instantiate(m_mushroomBox, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("LifeMushroomBox"))
			{
				l_newObject = (GameObject)Instantiate(m_lifeMushroomBox, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("CoinBox"))
			{
				l_newObject = (GameObject)Instantiate(m_coinBox, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("StarBox"))
			{
				l_newObject = (GameObject)Instantiate(m_starBox, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("EnemyNormal"))
			{
				l_newObject = (GameObject)Instantiate(m_enemyNormal, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("EnemyTortoise"))
			{
				l_newObject = (GameObject)Instantiate(m_enemyTortoise, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("Coin"))
			{
				l_newObject = (GameObject)Instantiate(m_coin, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("Pipeline"))
			{
				float l_height = int.Parse(l_intiParams[4].InnerText);
				l_position.y += (l_height + l_position.y) / 2;
				l_newObject = (GameObject)Instantiate(m_pipeline, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
				l_newObject.transform.localScale = new Vector3(1, l_height, 1);
			}

			if (l_name.Equals("CoinBrick"))
			{
				l_newObject = (GameObject)Instantiate(m_coinBrick, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}

			if (l_name.Equals("Stone"))
			{
				l_newObject = (GameObject)Instantiate(m_coinBrick, l_position, new Quaternion(0, 0, 0, 0));
				l_newObject.transform.parent = gameObject.transform;
			}
			//GameObject l_ganmeObject = Instantiate(
		}
	}
}
