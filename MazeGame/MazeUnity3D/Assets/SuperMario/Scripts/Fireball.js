#pragma strict
var m_startSpeed : float = 4.0;
private var m_speed : Vector3;

function Start ()
{
	m_speed = new Vector3(0, 0, m_startSpeed);
}

function Update () {
	if(transform.position.y < -5)
	{
		Destroy(gameObject);
	}
	transform.Translate(m_speed * Time.deltaTime, Space.Self);
}

function OnCollisionEnter (collision : Collision)
{
	if (collision.gameObject.tag == "EnemyTortoise" || collision.gameObject.tag == "Enemy")
	{
		collision.gameObject.SendMessage("ApplyFire", SendMessageOptions.DontRequireReceiver);
		Destroy(gameObject);
	} 
	else if (Mathf.Abs(transform.position.z - collision.gameObject.transform.position.z) <= 0.5
		&& transform.position.y > collision.gameObject.transform.position.y)
	{
		//Nothing To Do
	}
	else
	{
		Destroy(gameObject);
	}
}