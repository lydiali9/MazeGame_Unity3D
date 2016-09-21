#pragma strict

var m_lifeCount : int = 1;

function Start ()
{

}

function Update ()
{

}

function ApplyHit ()
{
	m_lifeCount--;
	if (m_lifeCount <= 0)
	{
		GameObject.FindGameObjectWithTag("SuperMario").BroadcastMessage("ApplyScore", 500, SendMessageOptions.DontRequireReceiver);
		Destroy(gameObject);
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