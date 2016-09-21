#pragma strict
var m_enemyObject : GameObject;
private var m_hasEnemy : boolean = true;

function Start () {

}

function Update () {
	var l_playerPosition : float = GameObject.FindGameObjectWithTag("PlayerObject").transform.position.z;
	if(m_hasEnemy && Mathf.Abs(l_playerPosition - transform.position.z) < 12)
	{
		m_hasEnemy = false;
		Instantiate(m_enemyObject, transform.position, transform.rotation);
	}
}