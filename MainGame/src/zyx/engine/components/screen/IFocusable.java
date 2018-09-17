package zyx.engine.components.screen;

interface IFocusable
{
	void onKeyPressed(char character);
	void onFocused();
	void onUnFocused();
}
