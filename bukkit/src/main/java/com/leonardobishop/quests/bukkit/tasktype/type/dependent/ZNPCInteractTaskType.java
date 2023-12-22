package com.leonardobishop.quests.bukkit.tasktype.type.dependent;

import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;
import com.leonardobishop.quests.bukkit.tasktype.BukkitTaskType;
import com.leonardobishop.quests.bukkit.util.TaskUtils;
import com.leonardobishop.quests.bukkit.util.chat.Chat;
import com.leonardobishop.quests.common.config.ConfigProblem;
import com.leonardobishop.quests.common.player.QPlayer;
import com.leonardobishop.quests.common.player.questprogressfile.TaskProgress;
import com.leonardobishop.quests.common.quest.Quest;
import com.leonardobishop.quests.common.quest.Task;
import io.github.znetworkw.znpcservers.npc.NPC;
import io.github.znetworkw.znpcservers.npc.interaction.NPCInteractEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class ZNPCInteractTaskType extends BukkitTaskType {

    private final BukkitQuestsPlugin plugin;

    public ZNPCInteractTaskType(BukkitQuestsPlugin plugin) {
        super("znpc_interact", TaskUtils.TASK_ATTRIBUTION_STRING, "Interact with an ZNPC to complete the quest.");
        this.plugin = plugin;

        super.addConfigValidator((config, problems) -> {
            if (config.containsKey("npc-uuid") && config.containsKey("npc-id")) {
                problems.add(new ConfigProblem(ConfigProblem.ConfigProblemType.WARNING,
                        "Both npc-name and npc-id is specified; npc-id will be ignored", null, "npc-id"));
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onNPCRightClick(NPCInteractEvent event) {
        if(event.isRightClick()){// 仅触发右键事件
            Player player = event.getPlayer();
            if (player.hasMetadata("NPC")) {
                return;
            }

            QPlayer qPlayer = plugin.getPlayerManager().getPlayer(player.getUniqueId());
            if (qPlayer == null) {
                return;
            }

            NPC npc = event.getNpc();

            for (TaskUtils.PendingTask pendingTask : TaskUtils.getApplicableTasks(player, qPlayer, this)) {
                Quest quest = pendingTask.quest();
                Task task = pendingTask.task();
                TaskProgress taskProgress = pendingTask.taskProgress();

                super.debug("Player clicked NPC", quest.getId(), task.getId(), player.getUniqueId());

                String configNPCId = (String) task.getConfigValue("npc-uuid");
                if (configNPCId != null) {
                    if (!npc.getUUID().toString().equals(configNPCId)) {
                        super.debug("NPC id " + npc.getUUID().toString() + " does not match required id", quest.getId(), task.getId(), player.getUniqueId());
                        continue;
                    }
                }

                super.debug("Marking task as complete", quest.getId(), task.getId(), player.getUniqueId());
                taskProgress.setCompleted(true);
            }
        }

    }
}
