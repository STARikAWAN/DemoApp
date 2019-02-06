package com.starik.dev;

import static com.github.appreciated.app.layout.entity.Section.FOOTER;
import static com.github.appreciated.app.layout.entity.Section.HEADER;

import com.github.appreciated.app.layout.behaviour.AppLayout;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder;
import com.github.appreciated.app.layout.component.appmenu.MenuHeaderComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftClickableComponent;
import com.github.appreciated.app.layout.component.appmenu.left.LeftNavigationComponent;
import com.github.appreciated.app.layout.component.appmenu.left.builder.LeftAppMenuBuilder;
import com.github.appreciated.app.layout.notification.DefaultNotificationHolder;
import com.github.appreciated.app.layout.notification.component.AppBarNotificationButton;
import com.github.appreciated.app.layout.router.AppLayoutRouterLayout;
import com.starik.dev.domain.User;
import com.starik.dev.views.GridTest;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.VaadinSession;

/**
 * The main view contains a button and a template element.
 */

@Push
@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainAppLayout extends AppLayoutRouterLayout {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Do not initialize here. This will lead to NPEs
     */
    private DefaultNotificationHolder notifications;

    @Override
    public AppLayout createAppLayoutInstance() {
        notifications = new DefaultNotificationHolder(newStatus -> {
        });

        return AppLayoutBuilder
                .get(Behaviour.LEFT_RESPONSIVE)
                .withTitle("App Layout")
                .withAppBar(AppBarBuilder
                        .get()
                        .add(new AppBarNotificationButton(VaadinIcon.BELL, notifications))
//                        .add(logout)
                        .build())
                .withAppMenu(LeftAppMenuBuilder
                        .get()
                        .addToSection(new MenuHeaderComponent("Welcome",
                                "Saad Tariq",
                                "/frontend/images/logo.png"
                        ), HEADER)
                        .add(new LeftNavigationComponent("Home", VaadinIcon.HOME.create(), MainView.class))
                        .add(new LeftNavigationComponent("Grid", VaadinIcon.TABLE.create(), GridTest.class))
                        .addToSection(new LeftClickableComponent("Sign Out", VaadinIcon.SIGN_OUT.create(), e ->
                        {
                        	VaadinSession.getCurrent().setAttribute(User.class.getName(), null);
                			UI.getCurrent().navigate("");
                        }), FOOTER)
                        .build())
                .build();
    }
}
