#pragma strict

@script RequireComponent( CharacterController )

// This script must be attached to a GameObject that has a CharacterController

public var m_cameraPivot : Transform;						// The transform used for camera rotation

public var m_forwardSpeed : float = 4;
public var m_jumpSpeed : float = 19;
public var m_inAirMultiplier : float = 0.25;					// Limiter for ground speed while jumping
public var m_sizeFlag : String = "Small";

public var m_fireball : GameObject;
public var m_fireable : boolean = false;

public var m_scripts : GameObject;
public var m_fireLaunch : Transform;

private var m_isMoving : int = 0;
private var m_isTurned : boolean = false;

private var m_reloadTime : float = 0.5;
private var m_lastShot = -10.0; 

private var m_thisTransform : Transform;
private var m_character : CharacterController;
private var m_cameraVelocity : Vector3;
private var m_velocity : Vector3;						// Used for continuing momentum while in air

// Moving platform support
private var m_activePlatform : Transform;
private var m_activeLocalPlatformPoint : Vector3;
private var m_activeGlobalPlatformPoint : Vector3;

function Start ()
{
	// Cache component lookup at startup instead of doing this every frame		
	m_thisTransform = GetComponent( Transform );
	m_character = GetComponent( CharacterController );
}

function OnEndGame()
{
	// Don't allow any more control changes when the game ends
	this.enabled = false;
}

function Update ()
{
	if (m_thisTransform.position.y < -5)
	{
		m_scripts.SendMessage("ReduceLife", SendMessageOptions.DontRequireReceiver);
	}
	
	if (m_thisTransform.position.z > 176)
	{
		m_scripts.SendMessage("WinSuperMario", SendMessageOptions.DontRequireReceiver);
	}

	var l_movement = new Vector3(0, 0, 0);

	// We only want horizontal movement
	l_movement.y = 0;
	l_movement.Normalize();

	var l_cameraTarget = Vector3.zero;
	
	if (!m_isTurned)
	{
		l_movement += new Vector3(0, 0, m_forwardSpeed * m_isMoving);
	}
	else
	{
		l_movement += new Vector3(0, 0, -m_forwardSpeed * m_isMoving);
	}
	
	// Check for jump
	if (!m_character.isGrounded)
	{
		// Apply gravity to our velocity to diminish it over time
		m_velocity.y += Physics.gravity.y * Time.deltaTime;
		
		// Move the camera back from the character when we jump
		l_cameraTarget.x = m_jumpSpeed * 0.25;
		
		// Adjust additional movement while in-air
		l_movement.z *= m_inAirMultiplier;
	}
	
	// Moving platform support
    if (m_activePlatform != null)
    {
        var l_newGlobalPlatformPoint = m_activePlatform.TransformPoint(m_activeLocalPlatformPoint);
        var l_moveDistance = (l_newGlobalPlatformPoint - m_activeGlobalPlatformPoint);
        if (l_moveDistance != Vector3.zero)
        {
			m_character.Move(l_moveDistance);
        }
    }
    
	m_activePlatform = null;
		
	l_movement += m_velocity;
	l_movement += Physics.gravity;
	l_movement *= Time.deltaTime;
	
	// Actually move the character	
	m_character.Move(l_movement);
	
	if ( m_character.isGrounded )
	{
		// Remove any persistent velocity after landing	
		m_velocity = Vector3.zero;
	}

	// Moving platforms support
    if (m_activePlatform != null)
    {
		m_activeGlobalPlatformPoint = transform.position;
		m_activeLocalPlatformPoint = m_activePlatform.InverseTransformPoint (transform.position);
    }
    
    // Seek camera towards target position
	var l_pos = m_cameraPivot.localPosition;
	l_pos.x = Mathf.SmoothDamp( l_pos.x, l_cameraTarget.x, m_cameraVelocity.x, 0.3 );
	l_pos.z = Mathf.SmoothDamp( l_pos.z, l_cameraTarget.z, m_cameraVelocity.z, 0.5 );
	m_cameraPivot.localPosition = l_pos;
}

function OnControllerColliderHit (p_hit : ControllerColliderHit)
{
    // Make sure we are really standing on a straight platform
    // Not on the underside of one and not falling down from it either!
    if (p_hit.moveDirection.y < -0.9 && p_hit.normal.y > 0.5)
    {
        m_activePlatform = p_hit.collider.transform;    
    }
    
    if (p_hit.collider.gameObject.tag.Equals("Brick"))
    {
		if (p_hit.gameObject.transform.position.y > p_hit.point.y
			&& Mathf.Abs(p_hit.gameObject.transform.position.z - transform.position.z) < 0.5)
		{
			m_velocity.y = 0;
			if (this.m_sizeFlag.Equals("Big"))
	    	{
				Destroy(p_hit.gameObject);
	    	}
	    	
			if (this.m_sizeFlag.Equals("Small"))
	    	{
	    		p_hit.gameObject.SendMessage("Shock", SendMessageOptions.DontRequireReceiver);
	    	}
		}
    }
    
    if (p_hit.collider.gameObject.tag.Equals("Box"))
    {
		if (p_hit.gameObject.transform.position.y > p_hit.point.y
			&& Mathf.Abs(p_hit.gameObject.transform.position.z - transform.position.z) < 0.5)
		{
			m_velocity.y = 0;
			p_hit.gameObject.SendMessage("HitByPlayer", SendMessageOptions.DontRequireReceiver);
		}
    }
    
    if (p_hit.collider.gameObject.tag.Equals("Flower"))
    {
    	m_scripts.SendMessage("ApplyScore", 1000, SendMessageOptions.DontRequireReceiver);
		if (m_sizeFlag.Equals("Big"))
    	{
			m_fireable = true;
    	}
    	if (m_sizeFlag.Equals("Small"))
    	{
	    	m_sizeFlag = "Big";
			gameObject.GetComponent(Animator).Play("PlayerBig");
    	}
	    Destroy(p_hit.collider.gameObject);
    }
    
    if (p_hit.collider.gameObject.tag.Equals("Enemy") || p_hit.collider.gameObject.tag.Equals("EnemyTortoise"))
    {
		if (p_hit.gameObject.transform.position.y < p_hit.point.y
			&& Mathf.Abs(p_hit.gameObject.transform.position.z - transform.position.z) < 0.5)
		{
			p_hit.gameObject.SendMessage("ApplyHit", SendMessageOptions.DontRequireReceiver);
		} 
		else
		{
			if (this.m_sizeFlag.Equals("Small"))
	    	{
				m_scripts.SendMessage("ReduceLife", SendMessageOptions.DontRequireReceiver);
	    	}
			if (this.m_sizeFlag.Equals("Big"))
	    	{
				gameObject.GetComponent(Animator).Play("PlayerSmall");
				this.m_sizeFlag = "Small";
	    	}
		}
    }
}

function Jump()
{
	if ( m_character.isGrounded )
	{
		m_velocity = m_character.velocity;	
		m_velocity.y = m_jumpSpeed;
	}
}

function Fire()
{
	if (m_fireable && Time.time > m_reloadTime + m_lastShot)
	{
		Instantiate (m_fireball, m_fireLaunch.position, m_fireLaunch.rotation);
		m_lastShot = Time.time;
	}
}

function Turn()
{
	m_isTurned = !m_isTurned;
	m_thisTransform.Rotate(0, 180, 0, Space.World );
}

function StartMoveForward()
{
	m_isMoving = 1;
}

function StartMoveBack()
{
	m_isMoving = -1;
}

function StopMove()
{
	m_isMoving = 0;
}

function StartCrouch()
{
	if(this.m_sizeFlag.Equals("Big"))
	{
		gameObject.GetComponent(Animator).Play("PlayerSmall");
	}
}

function StopCrouch()
{
	if(this.m_sizeFlag.Equals("Big"))
	{
		gameObject.GetComponent(Animator).Play("PlayerBig");
	}
}