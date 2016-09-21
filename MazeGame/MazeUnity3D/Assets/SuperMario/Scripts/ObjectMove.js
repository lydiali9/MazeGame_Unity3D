#pragma strict

var m_startSpeed : float = 2.0;
var m_speed : Vector3;

function Start ()
{
	m_speed = new Vector3(0, 0, m_startSpeed);
}

function Update ()
{
	if(transform.position.y < -5)
	{
		Destroy(gameObject);
	}
	if (m_speed.z > 0)
	{
		transform.rotation = new Quaternion(0, 0, 0, 0);
	}
	if (m_speed.z < 0)
	{
		transform.rotation = new Quaternion(0, 180, 0, 0);
	}
	transform.Translate(m_speed * Time.deltaTime, Space.World);
}

function OnCollisionEnter (collision : Collision)
{
	if (collision.gameObject.tag == "Ground" || collision.gameObject.tag == "Box" || collision.gameObject.tag == "Brick")
	{
		if (Mathf.Abs(transform.position.y - collision.gameObject.transform.position.y) < 0.5)
		{
			m_speed.z = -m_speed.z;
		}
	}
	else
	{
		if (gameObject.tag == "EnemyTortoise" && collision.gameObject.tag == "Enemy")
		{
			if (gameObject.GetComponent(EnemyTortoise).m_isHit)
			{
				return;
			}
		}
		m_speed.z = -m_speed.z;
	}
}