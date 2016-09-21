#pragma strict

var m_hasMushroom : boolean = false;
var m_hasLifeMushroom : boolean = false;
var m_hasCoin : boolean = false;
var m_hasStar : boolean = false;
var m_coinCount : int = 0;
var m_mushroomObject : GameObject;
var m_lifeMushroomObject : GameObject;
var m_starObject : GameObject;
var m_flowerObject : GameObject;
var m_scripts : GameObject;

function Start () {
	
}

function Update () {

}

function Shock()
{
	transform.Translate(new Vector3(0, 0.2, 0));
	yield WaitForSeconds(0.1);
	transform.Translate(new Vector3(0, -0.2, 0));
}

function HitByPlayer()
{
	if (m_hasMushroom)
	{
		m_hasMushroom = false;
		Shock();
		if (GameObject.FindGameObjectWithTag("PlayerObject").GetComponent(CharacterControl).m_sizeFlag == "Big")
		{
			yield WaitForSeconds(0.1);
			Instantiate (m_flowerObject, transform.position + new Vector3(0, 1, 0), transform.rotation);
		}
		else
		{
			yield WaitForSeconds(0.1);
			Instantiate (m_mushroomObject, transform.position + new Vector3(0, 1, 0), transform.rotation);
		}
	}
	
	if (m_hasLifeMushroom)
	{
		m_hasLifeMushroom = false;
		Shock();
		yield WaitForSeconds(0.1);
		Instantiate (m_lifeMushroomObject, transform.position + new Vector3(0, 1, 0), transform.rotation);
	}
	
	if (m_hasCoin && m_coinCount > 0)
	{
		m_coinCount--;
		if (m_coinCount <= 0)
		{
			m_hasCoin = false;
		}
		GameObject.FindGameObjectWithTag("SuperMario").BroadcastMessage("ApplyScore", 100, SendMessageOptions.DontRequireReceiver);
		GameObject.FindGameObjectWithTag("SuperMario").BroadcastMessage("ApplyCoin", SendMessageOptions.DontRequireReceiver);
		Shock();
	}
	
	if (m_hasStar)
	{
		m_hasStar = false;
		Shock();
		yield WaitForSeconds(0.1);
		Instantiate (m_starObject, transform.position + new Vector3(0, 1, 0), transform.rotation);
	}
}