#pragma strict

public var m_character : CharacterControl;
public var m_scripts : GameObject;
public var m_animator : Animator;

function Start ()
{
}

function Update ()
{

}

function OnTriggerEnter(p_collider : Collider)
{
	if (m_character)
	{
		if (p_collider.gameObject.tag == "Mushroom")
		{
			m_scripts.SendMessage("ApplyScore", 1000, SendMessageOptions.DontRequireReceiver);
			m_character.m_sizeFlag = "Big";
			m_animator.Play("PlayerBig");
			Destroy(p_collider.gameObject);
		}
		
		if (p_collider.gameObject.tag == "LifeMushroom")
		{
			m_scripts.SendMessage("ApplyLife", SendMessageOptions.DontRequireReceiver);
			Destroy(p_collider.gameObject);
		}
		
		if (p_collider.collider.gameObject.tag == "Flower")
	    {
	    	m_scripts.SendMessage("ApplyScore", 1000, SendMessageOptions.DontRequireReceiver);
			if (m_character.m_sizeFlag == "Big")
	    	{
				m_character.m_fireable = true;
	    	}
	    	if (m_character.m_sizeFlag == "Small")
	    	{
		    	m_character.m_sizeFlag = "Big";
				m_animator.Play("PlayerBig");
	    	}
	    	Destroy(p_collider.gameObject);
	    }
	    
	    if (p_collider.collider.gameObject.tag == "Coin")
	    {
	    	m_scripts.SendMessage("ApplyScore", 100, SendMessageOptions.DontRequireReceiver);
	    	m_scripts.SendMessage("ApplyCoin", SendMessageOptions.DontRequireReceiver);
	    	Destroy(p_collider.gameObject);
	    }
		
		if (p_collider.gameObject.tag == "Enemy" || p_collider.gameObject.tag == "EnemyTortoise")
	    {
		    if (p_collider.gameObject.transform.position.y > transform.position.y)
			{
				if (m_character.m_sizeFlag == "Small")
		    	{
					m_scripts.SendMessage("ReduceLife", SendMessageOptions.DontRequireReceiver);
		    	}
		    	
				if (m_character.m_sizeFlag == "Big")
		    	{
					m_animator.Play("PlayerSmall");
					m_character.m_sizeFlag = "Small";
					m_character.m_fireable = false;
		    	}
			} 
	    }
	}

}