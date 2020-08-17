package zyx.engine.components.screen.base.generic.window.scroll;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.Quad;
import zyx.engine.components.screen.base.generic.window.WindowsButton;
import zyx.engine.components.screen.image.Scale9Image;
import zyx.engine.components.screen.interactable.InteractableContainer;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsScrollView extends DisplayObjectContainer implements IScrollViewScrolled
{
	private static final int SCROLL_BTN_SIZE = 16;
	private static final int SCROLLER_SIZE = 32;
	private static final int FRAME_PADDING = 2;
	private static final int FRAME_PADDING_2 = FRAME_PADDING + FRAME_PADDING;
	
	private DisplayObjectContainer viewContainer;
	private DisplayObject view;
	private Scale9Image bg;
	
	private WindowsButton scrollUpBtn;
	private WindowsButton scrollDownBtn;
	private Quad scrollBackground;
	private WindowsScrollButton scroller;
	
	private float viewHeight;
	private int scrollHeight;
	private float scrollPerPixel;
	private float currentScroll;
	private int leftoverScroll;
	private float scrollPerClick;
	private float maxClicks;
	private float scrollerPerClick;
	
	private ICallback<InteractableContainer> scrollBtnClicked;
	private ICallback<Integer> scrollViewHeightChanged;
	
	public WindowsScrollView(int width, int height)
	{
		scrollBtnClicked = this::onScrollBtnClicked;
		scrollViewHeightChanged = this::onScrollViewHeightChanged;
		
		bg = new Scale9Image();
		bg.load("container_down");
		bg.setSize(width, height);
		addChild(bg);
		
		viewContainer = new DisplayObjectContainer();
		viewContainer.setPosition(true, 2, 2);
		viewContainer.setClipRect(0, 0, width - FRAME_PADDING_2, height - FRAME_PADDING_2);
		addChild(viewContainer);
		
		scrollUpBtn = new WindowsButton();
		scrollDownBtn = new WindowsButton();
		scroller = new WindowsScrollButton(this);
		scroller.setHeight(SCROLLER_SIZE);
		scrollBackground = new Quad(SCROLL_BTN_SIZE, height, 0xD4D0C8);
				
		scrollBackground.setX(width - SCROLL_BTN_SIZE);
		scrollUpBtn.setX(width - SCROLL_BTN_SIZE);
		scrollDownBtn.setX(width - SCROLL_BTN_SIZE);
		scroller.setX(width - SCROLL_BTN_SIZE);
		
		scroller.setY(SCROLL_BTN_SIZE);
		scrollDownBtn.setY(height - SCROLL_BTN_SIZE);
		
		scrollHeight = height - FRAME_PADDING_2;
		leftoverScroll = scrollHeight - SCROLLER_SIZE - SCROLL_BTN_SIZE - SCROLL_BTN_SIZE + FRAME_PADDING_2;
		
		scrollUpBtn.onButtonClicked.addCallback(scrollBtnClicked);
		scrollDownBtn.onButtonClicked.addCallback(scrollBtnClicked);
	}
	
	public void setView(DisplayObject disp)
	{
		view = disp;
		viewContainer.addChild(view);
		view.setPosition(true, 0, 0);
		
		if (view instanceof IScrollableView)
		{
			IScrollableView scrollable = (IScrollableView) view;
			viewHeight = scrollable.getTotalHeight();
			scrollable.addHeightChangedListener(scrollViewHeightChanged);
		}
		else
		{
			viewHeight = view.getHeight();
		}
		
		rescaleComponents();
	}

	@Override
	protected void onDraw()
	{
		super.onDraw();
	}

	private void rescaleComponents()
	{
		addChild(scrollBackground);
		addChild(scrollUpBtn);
		addChild(scrollDownBtn);
		addChild(scroller);
		
		scroller.setY(16);
		if (viewHeight > scrollHeight)
		{
			scroller.setHeight(SCROLLER_SIZE);
			scrollPerPixel = viewHeight / leftoverScroll;
			float hiddenView = viewHeight - scrollHeight;
			float movePerClick = 10;
			maxClicks = hiddenView / movePerClick;
			scrollPerClick = hiddenView / maxClicks;
			scrollerPerClick = leftoverScroll / maxClicks;
			scroller.setLimits(SCROLL_BTN_SIZE, SCROLL_BTN_SIZE + leftoverScroll);
		}
		else
		{
			scroller.setHeight(scrollHeight - SCROLL_BTN_SIZE - SCROLL_BTN_SIZE);
			scroller.setLimits(0, 0);
		}
		
		currentScroll = 0;
	}

	private void onScrollViewHeightChanged(Integer height)
	{
		viewHeight = height;
		rescaleComponents();
	}
	
	private void onScrollBtnClicked(InteractableContainer btn)
	{
		if (btn == scrollUpBtn)
		{
			currentScroll--;
			if (currentScroll < 0)
			{
				currentScroll = 0;
			}
		}
		else if (btn == scrollDownBtn)
		{
			currentScroll++;
			if (currentScroll > maxClicks)
			{
				currentScroll = maxClicks;
			}
		}
		
		scroller.setScroll(currentScroll * scrollerPerClick);
		scroller.setY(16 + (currentScroll * scrollerPerClick));
		view.setY(-(currentScroll * scrollPerClick));
	}

	@Override
	public void onScrolled(float pixels)
	{
		pixels -= SCROLL_BTN_SIZE;
		
		float fraction = -(pixels / leftoverScroll);
		float toScroll = viewHeight - scrollHeight;
		view.setY(fraction * toScroll);
		
		currentScroll = (int) -((fraction * toScroll) / scrollPerClick);
	}
}
