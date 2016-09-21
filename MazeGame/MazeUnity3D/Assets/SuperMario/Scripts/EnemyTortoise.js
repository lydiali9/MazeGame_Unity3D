#pragma strict

private var m_lifeCount : int = 1;
var m_isHit : boolean = false;
private var m_isStop : boolean = false;
private var m_speedJS : ObjectMove;

function Start ()
{
	m_speedJS = gameObject.GetComponent(ObjectMove);
}

function Update ()
{

}

function ApplyHit ()
{
	if (!m_isHit)
	{
		GameObject.FindGameObjectWithTag("SuperMario").BroadcastMessage("ApplyScore", 500, SendMessageOptions.DontRequireReceiver);
		m_isHit = true;
		m_speedJS.m_speed.z = 0;
		m_isStop = true;
	}
	else
	{
		if (!m_isStop)
		{
			m_speedJS.m_speed.z = 0;
			m_isStop = true;
		}
		else
		{
			m_speedJS.m_speed.z = Mathf.Sign(gameObject.transform.position.z 
				- GameObject.FindGameObjectWithTag("PlayerObject").transform.position.z)
				* m_speedJS.m_startSpeed * 2;
			m_isStop = false;
		}
	}
}

function ApplyFire ()
{
	m_lifeCount--;
	if (m_lifeCount <= 0)
	{
		GameObject.FindGameObjectWithTag("SuperMario").BroadcastMessage("ApplyScore", 500, SendMessageOptions.DontRequireReceiver);
		Destroy(gameObject);
	}
}

function OnCollisionEnter (collision : Collision)
{
	if (collision.gameObject.tag == "Enemy" && m_isHit && !m_isStop)
	{
		collision.gameObject.SendMessage("ApplyFire", SendMessageOptions.DontRequireReceiver);
	}
}