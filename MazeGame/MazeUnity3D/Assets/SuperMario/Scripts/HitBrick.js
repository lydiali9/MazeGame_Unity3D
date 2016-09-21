#pragma strict

function Start ()
{

}

function Update ()
{

}

function Shock()
{
	transform.Translate(new Vector3(0, 0.2, 0));
	yield WaitForSeconds(0.1);
	transform.Translate(new Vector3(0, -0.2, 0));
}